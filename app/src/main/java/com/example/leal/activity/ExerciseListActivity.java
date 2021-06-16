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
import com.example.leal.domains.Exercise;
import com.example.leal.domains.ExerciseResponse;
import com.example.leal.domains.ExerciseType;
import com.example.leal.domains.User;
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
    private TextView exerciseListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        findViewsById();
        String idTraining = retrieverDataFromTrainingListActivity();
        User user = retrieverUserFromLoginActivity();
        setupExerciseListToolBar(idTraining);
        getExerciseListFromFirebase(user, idTraining);
        setupExerciseListButton();
        showProgressDialog();
    }

    private void getExerciseListFromFirebase(User user, String idTraining) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.USERS_COLLECTION_PATH)
                .document(Constants.EMAIL_DOCUMENT_PATH)
                .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                .document(idTraining)
                .collection(Constants.EXERCISE_LIST_FIELD_TRAINING_LIST)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        loginProgressDialog.dismiss();
                        exerciseListRecyclerView.setVisibility(View.VISIBLE);
                        ArrayList<ExerciseResponse> exerciseListResponse = new ArrayList<>();
                        Exercise exercise;
                        ArrayList<Exercise> exerciseList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ExerciseResponse exerciseResponse =
                                    document.toObject(ExerciseResponse.class);
                            exerciseResponse.setDocumentId(document.getId());
                            exerciseListResponse.add(exerciseResponse);
                            exercise = new Exercise(
                                    exerciseResponse.getId(),
                                    exerciseResponse.getObservation(),
                                    ExerciseType.toExerciseType(exerciseResponse.getType()),
                                    exerciseResponse.getDocumentId()
                            );
                            exerciseList.add(exercise);
                        }
                        setupRecyclerView(exerciseList, user, idTraining);
                    } else if (task.getException() instanceof FirebaseNetworkException) {
                        loginProgressDialog.dismiss();
                        exerciseListTextView.setVisibility(View.VISIBLE);
                    } else {
                        loginProgressDialog.dismiss();
                        exerciseListTextView.setText(getString(R.string.error_exercise_list));
                        exerciseListTextView.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void setupRecyclerView(ArrayList<Exercise> exerciseList, User user, String idTraining) {
        ExerciseListAdapter exerciseListAdapter = new ExerciseListAdapter(exerciseList, this,
                exercise -> Utils.createAlertDialogWithQuestion(getString(R.string.item_exercise_delete_message),
                        this, (dialog, which) -> deleteExercise(user, exercise, idTraining)
                )
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

    private void deleteExercise(User user, Exercise exercise, String idTraining) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.USERS_COLLECTION_PATH)
                .document(Constants.EMAIL_DOCUMENT_PATH)
                .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                .document(idTraining)
                .collection(Constants.EXERCISE_LIST_FIELD_TRAINING_LIST)
                .document(exercise.getDocumentId())
                .delete()
                .addOnSuccessListener(unused -> Toast.makeText(ExerciseListActivity.this,
                        getString(R.string.deleted_exercise), Toast.LENGTH_LONG
                ).show())
                .addOnFailureListener(e -> Toast.makeText(ExerciseListActivity.this,
                        getString(R.string.error_deleted_exercise), Toast.LENGTH_LONG
                ).show());
    }

    private User retrieverUserFromLoginActivity() {
        return (User) getIntent().getSerializableExtra(Constants.USER);
    }

    private void showProgressDialog() {
        loginProgressDialog = new ProgressDialog(this);
        loginProgressDialog.show();
        loginProgressDialog.setContentView(R.layout.progress_dialog);
        loginProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loginProgressDialog.setCancelable(false);
    }

    private String retrieverDataFromTrainingListActivity() {
        return getIntent().getStringExtra(Constants.TRAINING_DETAILS);
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
        exerciseListButton.setOnClickListener(v -> moveToNewExerciseActivity());
    }

    private void moveToNewExerciseActivity() {
        Intent intent = new Intent(this, NewExerciseActivity.class);
        startActivity(intent);
    }

    private void setupExerciseListToolBar(String idTraining) {
        setSupportActionBar(exerciseListToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.exercise_list) + Constants.BLANK_SPACE + idTraining);
        }
    }

    private void findViewsById() {
        exerciseListToolBar = findViewById(R.id.exerciseListToolBar);
        exerciseListRecyclerView = findViewById(R.id.exerciseListRecyclerView);
        exerciseListButton = findViewById(R.id.exerciseListButton);
        exerciseListTextView = findViewById(R.id.exerciseListTextView);
    }
}