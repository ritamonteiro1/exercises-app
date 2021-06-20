package com.example.leal.activity.exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leal.R;
import com.example.leal.adapter.ExerciseListAdapter;
import com.example.leal.click.listener.exercise.click.listener.OnExerciseDeleteClickListener;
import com.example.leal.click.listener.exercise.click.listener.OnExerciseEditClickListener;
import com.example.leal.constants.Constants;
import com.example.leal.domains.exercise.Exercise;
import com.example.leal.domains.exercise.ExerciseResponse;
import com.example.leal.domains.exercise.ExerciseType;
import com.example.leal.utils.Utils;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ExerciseListActivity extends AppCompatActivity {
    private Toolbar exerciseListToolBar;
    private RecyclerView exerciseListRecyclerView;
    private Button exerciseListButton;
    private ProgressDialog progressDialog;
    private TextView exerciseListIdNumberTextView,
            exerciseListIdTitleTextView, exerciseListEmptyTextView;
    private String loggedUserEmail, trainingNumberId, trainingDocumentId;
    private View exerciseListDivisionView;
    private CollectionReference exerciseListCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        findViewsById();
        loggedUserEmail = retrieverLoggedUserEmail();
        trainingNumberId = retrieverTrainingNumberId();
        trainingDocumentId = retrieverTrainingDocumentId();
        setupExerciseListToolBar();
        setupExerciseListCollection();
        setupExerciseListButton();
        progressDialog = Utils.showProgressDialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getExerciseListFromFirebase();
    }

    private void setupExerciseListCollection() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        exerciseListCollection = db.collection(Constants.USERS_COLLECTION_PATH)
                .document(loggedUserEmail)
                .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                .document(trainingDocumentId)
                .collection(Constants.EXERCISE_LIST_COLLECTION_PATH);
    }

    private String retrieverTrainingDocumentId() {
        return getIntent().getStringExtra(Constants.TRAINING_DOCUMENT_ID);
    }

    private void getExerciseListFromFirebase() {
        exerciseListCollection
                .get()
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    exerciseListEmptyTextView.setVisibility(View.GONE);
                    if (task.isSuccessful() && task.getResult() != null) {
                        successfullyGetExerciseListFromFirebase(loggedUserEmail, trainingNumberId
                                , trainingDocumentId, task.getResult());
                    } else if (task.getException() instanceof FirebaseNetworkException) {
                        Utils.createErrorDialog(getString(R.string.error_connection_fail),
                                getString(R.string.alert_dialog_positive_message_ok), this
                        );
                    } else {
                        Utils.createErrorDialog(getString(R.string.generic_error_try_again),
                                getString(R.string.alert_dialog_positive_message_ok), this
                        );
                    }
                });
    }

    private void successfullyGetExerciseListFromFirebase(String loggedUserEmail,
                                                         String trainingNumberId,
                                                         String trainingDocumentId,
                                                         QuerySnapshot result) {
        exerciseListIdNumberTextView.setText(trainingNumberId);
        exerciseListIdNumberTextView.setVisibility(View.VISIBLE);
        exerciseListIdTitleTextView.setVisibility(View.VISIBLE);
        exerciseListRecyclerView.setVisibility(View.VISIBLE);
        exerciseListDivisionView.setVisibility(View.VISIBLE);
        List<Exercise> exerciseList = new ArrayList<>();
        if (result != null && !result.isEmpty()) {
            mapExerciseResponseList(result, exerciseList);
        } else {
            exerciseListEmptyTextView.setVisibility(View.VISIBLE);
        }
        setupRecyclerView(exerciseList, loggedUserEmail, trainingDocumentId);
    }

    private void mapExerciseResponseList(QuerySnapshot result,
                                         List<Exercise> exerciseList) {
        Exercise exercise;
        for (QueryDocumentSnapshot document : result) {
            ExerciseResponse exerciseResponse =
                    document.toObject(ExerciseResponse.class);
            exerciseResponse.setDocumentId(document.getId());
            exercise = new Exercise(
                    exerciseResponse.getId(),
                    exerciseResponse.getObservation(),
                    ExerciseType.toExerciseType(exerciseResponse.getType()),
                    exerciseResponse.getDocumentId(),
                    exerciseResponse.getUrlImage()
            );
            exerciseList.add(exercise);
        }
    }

    private void setupRecyclerView(List<Exercise> exerciseList, String loggedUserEmail,
                                   String trainingDocumentId) {
        ExerciseListAdapter exerciseListAdapter = new ExerciseListAdapter(exerciseList, this,
                setupDeleteExerciseClickListener(),
                setupEditExerciseClickListener(loggedUserEmail, trainingDocumentId)
        );
        setupAdapter(exerciseListAdapter);
    }

    @NotNull
    private OnExerciseEditClickListener setupEditExerciseClickListener(String loggedUserEmail,
                                                                       String trainingDocumentId) {
        return exerciseDocumentId -> {
            Intent intent = new Intent(ExerciseListActivity.this, EditExerciseActivity.class);
            intent.putExtra(Constants.EXERCISE_DOCUMENT_ID, exerciseDocumentId);
            intent.putExtra(Constants.LOGGED_USER_EMAIL, loggedUserEmail);
            intent.putExtra(Constants.TRAINING_DOCUMENT_ID, trainingDocumentId);
            startActivity(intent);
        };
    }

    @NotNull
    private OnExerciseDeleteClickListener setupDeleteExerciseClickListener() {
        return exercise -> Utils.createAlertDialogWithQuestion(getString(R.string.exercise_list_delete_question_exercise),
                this, (dialog, which) -> deleteExercise(exercise)
        );
    }

    private void setupAdapter(ExerciseListAdapter exerciseListAdapter) {
        exerciseListRecyclerView.setAdapter(exerciseListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false
        );
        exerciseListRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout
                .VERTICAL));
        exerciseListRecyclerView.setLayoutManager(layoutManager);
    }

    private void deleteExercise(Exercise exercise) {
        exerciseListCollection
                .document(exercise.getDocumentId())
                .delete()
                .addOnSuccessListener(unused -> {
                            Toast.makeText(
                                    getApplicationContext(),
                                    getString(R.string.exercise_list_successful_deleted_exercise),
                                    Toast.LENGTH_LONG
                            ).show();
                            getExerciseListFromFirebase();
                        }
                ).addOnFailureListener(e ->
                Utils.createErrorDialog(getString(R.string.generic_error_try_again),
                        getString(R.string.alert_dialog_positive_message_ok), this
                ));
    }

    private String retrieverLoggedUserEmail() {
        return getIntent().getStringExtra(Constants.LOGGED_USER_EMAIL);
    }

    private String retrieverTrainingNumberId() {
        return getIntent().getStringExtra(Constants.TRAINING_NUMBER_ID);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupExerciseListButton() {
        exerciseListButton.setOnClickListener(v -> moveToNewExerciseActivity(
                loggedUserEmail,
                trainingDocumentId
        ));
    }

    private void moveToNewExerciseActivity(String loggedUserEmail, String trainingDocumentId) {
        Intent intent = new Intent(this, NewExerciseActivity.class);
        intent.putExtra(Constants.LOGGED_USER_EMAIL, loggedUserEmail);
        intent.putExtra(Constants.TRAINING_DOCUMENT_ID, trainingDocumentId);
        startActivity(intent);
    }

    private void setupExerciseListToolBar() {
        setSupportActionBar(exerciseListToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.exercise_list_tool_bar_title));
        }
    }

    private void findViewsById() {
        exerciseListToolBar = findViewById(R.id.exerciseListToolBar);
        exerciseListRecyclerView = findViewById(R.id.exerciseListRecyclerView);
        exerciseListButton = findViewById(R.id.exerciseListButton);
        exerciseListIdNumberTextView = findViewById(R.id.exerciseListIdNumberTextView);
        exerciseListIdTitleTextView = findViewById(R.id.exerciseListIdTitleTextView);
        exerciseListEmptyTextView = findViewById(R.id.exerciseListEmptyTextView);
        exerciseListDivisionView = findViewById(R.id.exerciseListDivisionView);
    }
}