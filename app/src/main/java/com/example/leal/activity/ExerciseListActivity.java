package com.example.leal.activity;

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
import com.example.leal.constants.Constants;
import com.example.leal.domains.ExerciseRequest;
import com.example.leal.domains.ExerciseResponse;
import com.example.leal.domains.ExerciseType;
import com.example.leal.utils.Utils;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;


public class ExerciseListActivity extends AppCompatActivity {
    private Toolbar exerciseListToolBar;
    private RecyclerView exerciseListRecyclerView;
    private Button exerciseListButton;
    private ProgressDialog loginProgressDialog;
    private TextView exerciseListTextView, exerciseListIdNumberTextView,
            exerciseListIdTitleTextView, exerciseListEmptyTextView;
    private String loggedUserEmail, trainingNumberId, trainingDocumentId;
    private View exerciseListDivisionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        findViewsById();
        loggedUserEmail = retrieverLoggedUserEmailFromTrainingListActivity();
        trainingNumberId = retrieverTrainingNumberIdFromTrainingListActivity();
        trainingDocumentId = retrieverTrainingDocumentIdFromTrainingListActivity();
        setupExerciseListToolBar();
        setupExerciseListButton(loggedUserEmail, trainingDocumentId);
        showProgressDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getExerciseListFromFirebase(loggedUserEmail, trainingNumberId, trainingDocumentId);
    }

    private String retrieverTrainingDocumentIdFromTrainingListActivity() {
        return getIntent().getStringExtra(Constants.TRAINING_DOCUMENT_ID);
    }

    private void getExerciseListFromFirebase(String loggedUserEmail, String trainingNumberId,
                                             String trainingDocumentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.USERS_COLLECTION_PATH)
                .document(loggedUserEmail)
                .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                .document(trainingDocumentId)
                .collection(Constants.EXERCISE_LIST_FIELD_TRAINING_LIST)
                .get()
                .addOnCompleteListener(task -> {
                    loginProgressDialog.dismiss();
                    if (task.isSuccessful() && task.getResult() != null) {
                        exerciseListIdNumberTextView.setText(trainingNumberId);
                        exerciseListIdNumberTextView.setVisibility(View.VISIBLE);
                        exerciseListIdTitleTextView.setVisibility(View.VISIBLE);
                        exerciseListRecyclerView.setVisibility(View.VISIBLE);
                        exerciseListDivisionView.setVisibility(View.VISIBLE);
                        ExerciseRequest exerciseRequest;
                        ArrayList<ExerciseRequest> exerciseRequestList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ExerciseResponse exerciseResponse =
                                    document.toObject(ExerciseResponse.class);
                            exerciseResponse.setDocumentId(document.getId());
                            exerciseRequest = new ExerciseRequest(
                                    exerciseResponse.getId(),
                                    exerciseResponse.getObservation(),
                                    ExerciseType.toExerciseType(exerciseResponse.getType()),
                                    exerciseResponse.getDocumentId(),
                                    exerciseResponse.getUrlImage()
                            );
                            exerciseRequestList.add(exerciseRequest);
                        }
                        setupRecyclerView(exerciseRequestList, loggedUserEmail, trainingDocumentId);
//                    } else if (task.getResult().isEmpty()) {
//                        exerciseListEmptyTextView.setVisibility(View.VISIBLE);
                    }else if (task.getException() instanceof FirebaseNetworkException) {
                        exerciseListTextView.setVisibility(View.VISIBLE);
                    } else {
                        exerciseListTextView.setText(getString(R.string.generic_error_try_again));
                        exerciseListTextView.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void setupRecyclerView(ArrayList<ExerciseRequest> exerciseRequestList, String loggedUserEmail,
                                   String trainingDocumentId) {
        ExerciseListAdapter exerciseListAdapter = new ExerciseListAdapter(exerciseRequestList, this,
                exerciseRequest -> Utils.createAlertDialogWithQuestion(getString(R.string.exercise_list_delete_question_exercise),
                        this, (dialog, which) -> deleteExercise(loggedUserEmail, exerciseRequest,
                                trainingDocumentId
                        )
                ), exerciseDocumentId -> {
            Intent intent = new Intent(ExerciseListActivity.this, EditExerciseActivity.class);
            intent.putExtra(Constants.EXERCISE_DOCUMENT_ID, exerciseDocumentId);
            intent.putExtra(Constants.LOGGED_USER_EMAIL, loggedUserEmail);
            intent.putExtra(Constants.TRAINING_DOCUMENT_ID, trainingDocumentId);
            startActivity(intent);
        }
        );
        setupAdapter(exerciseListAdapter);
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

    private void deleteExercise(String loggedUserEmail, ExerciseRequest exerciseRequest,
                                String trainingDocumentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.USERS_COLLECTION_PATH)
                .document(loggedUserEmail)
                .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                .document(trainingDocumentId)
                .collection(Constants.EXERCISE_LIST_FIELD_TRAINING_LIST)
                .document(exerciseRequest.getDocumentId())
                .delete()
                .addOnSuccessListener(unused -> Toast.makeText(
                        ExerciseListActivity.this,
                        getString(R.string.exercise_list_successful_deleted_exercise),
                        Toast.LENGTH_LONG
                ).show())
                .addOnFailureListener(e -> Toast.makeText(ExerciseListActivity.this,
                        getString(R.string.generic_error_try_again), Toast.LENGTH_LONG
                ).show());
    }

    private String retrieverLoggedUserEmailFromTrainingListActivity() {
        return getIntent().getStringExtra(Constants.LOGGED_USER_EMAIL);
    }

    private void showProgressDialog() {
        loginProgressDialog = new ProgressDialog(this);
        loginProgressDialog.show();
        loginProgressDialog.setContentView(R.layout.progress_dialog);
        loginProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loginProgressDialog.setCancelable(false);
    }

    private String retrieverTrainingNumberIdFromTrainingListActivity() {
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

    private void setupExerciseListButton(String loggedUserEmail, String trainingDocumentId) {
        exerciseListButton.setOnClickListener(v -> moveToNewExerciseActivity(loggedUserEmail, trainingDocumentId));
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
        exerciseListTextView = findViewById(R.id.exerciseListTextView);
        exerciseListIdNumberTextView = findViewById(R.id.exerciseListIdNumberTextView);
        exerciseListIdTitleTextView = findViewById(R.id.exerciseListIdTitleTextView);
        exerciseListEmptyTextView = findViewById(R.id.exerciseListEmptyTextView);
        exerciseListDivisionView = findViewById(R.id.exerciseListDivisionView);
    }
}