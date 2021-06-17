package com.example.leal.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leal.R;
import com.example.leal.adapter.TrainingListAdapter;
import com.example.leal.constants.Constants;
import com.example.leal.domains.TrainingResponse;
import com.example.leal.utils.Utils;

import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;
import java.util.List;


public class TrainingListActivity extends AppCompatActivity {
    private RecyclerView trainingListRecyclerView;
    private Button trainingListButton;
    private Toolbar trainingListToolBar;
    private TextView trainingListTextView;
    private ProgressDialog loginProgressDialog;
    private String loggedUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);
        findViewsById();
        loggedUserEmail = retrieverLoggedUserEmailFromLoginActivity();
        setupTrainingListButton(loggedUserEmail);
        setupTrainingListToolBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showProgressDialog();
        getTrainingListFromFirebase(loggedUserEmail);
    }

    private String retrieverLoggedUserEmailFromLoginActivity() {
        return getIntent().getStringExtra(Constants.LOGGED_USER_EMAIL);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupTrainingListToolBar() {
        setSupportActionBar(trainingListToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.training_list_tool_bar_title));
        }
    }

    private void setupTrainingListButton(String loggedUserEmail) {
        trainingListButton.setOnClickListener(v -> moveToNewTrainingActivity(loggedUserEmail));
    }

    private void moveToNewTrainingActivity(String loggedUserEmail) {
        Intent intent = new Intent(this, NewTrainingActivity.class);
        intent.putExtra(Constants.LOGGED_USER_EMAIL, loggedUserEmail);
        startActivity(intent);
    }

    public void getTrainingListFromFirebase(String loggedUserEmail) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.USERS_COLLECTION_PATH)
                .document(loggedUserEmail)
                .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                .orderBy(Constants.TRAINING_TIMESTAMP)
                .get()
                .addOnCompleteListener(task -> {
                    loginProgressDialog.dismiss();
                    if (task.isSuccessful() && task.getResult() != null) {
                        successfullyGetTrainingListFromFirebase(loggedUserEmail, task);
//                    } else if (task.getResult().isEmpty()){
//                        trainingListTextView.setText(getString(R.string.training_list_empty));
//                        trainingListTextView.setVisibility(View.VISIBLE);
                    } else if (task.getException() instanceof FirebaseNetworkException) {
                        trainingListTextView.setVisibility(View.VISIBLE);
                    } else {
                        trainingListTextView.setText(getString(R.string.generic_error_try_again));
                        trainingListTextView.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void successfullyGetTrainingListFromFirebase(String loggedUserEmail,
                                                         com.google.android.gms.tasks.Task<com.google.firebase.firestore.QuerySnapshot> task) {
        trainingListRecyclerView.setVisibility(View.VISIBLE);
        ArrayList<TrainingResponse> trainingResponseList = new ArrayList<>();
        if (task.getResult() != null)
            for (QueryDocumentSnapshot document : task.getResult()) {
                TrainingResponse trainingResponse = document.toObject(TrainingResponse.class);
                trainingResponse.setDocumentId(document.getId());
                trainingResponseList.add(trainingResponse);
            }
        setupRecyclerView(trainingResponseList, loggedUserEmail);
    }

    private void setupRecyclerView(List<TrainingResponse> trainingResponseList, String loggedUserEmail) {
        TrainingListAdapter trainingListAdapter = new TrainingListAdapter(
                trainingResponseList,
                this,
                training -> Utils.createAlertDialogWithQuestion(
                        getString(R.string.training_list_delete_question_training),
                        this,
                        (dialog, which) -> deleteTraining(loggedUserEmail, training)
                ), trainingDocumentId -> {
            Intent intent = new Intent(TrainingListActivity.this, EditTrainingActivity.class);
            intent.putExtra(Constants.TRAINING_DOCUMENT_ID, trainingDocumentId);
            intent.putExtra(Constants.LOGGED_USER_EMAIL, loggedUserEmail);
            startActivity(intent);
        }, (trainingNumberId, trainingDocumentId) -> {
            Intent intent = new Intent(TrainingListActivity.this, ExerciseListActivity.class);
            intent.putExtra(Constants.TRAINING_NUMBER_ID, trainingNumberId);
            intent.putExtra(Constants.TRAINING_DOCUMENT_ID, trainingDocumentId);
            intent.putExtra(Constants.LOGGED_USER_EMAIL, loggedUserEmail);
            startActivity(intent); }
        );
        setupAdapter(trainingListAdapter);
    }

    private void deleteTraining(String loggedUserEmail, TrainingResponse trainingResponse) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.USERS_COLLECTION_PATH)
                .document(loggedUserEmail)
                .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                .document(trainingResponse.getDocumentId())
                .delete()
                .addOnSuccessListener(
                        unused -> {
                            Toast.makeText(
                                    TrainingListActivity.this,
                                    getString(R.string.training_list_successfully_deleted_training),
                                    Toast.LENGTH_LONG
                            ).show();
                            getTrainingListFromFirebase(loggedUserEmail);
                        })
                .addOnFailureListener(e -> Toast.makeText(
                        TrainingListActivity.this,
                        getString(R.string.training_list_error_deleted_training),
                        Toast.LENGTH_LONG
                ).show());
    }

    private void setupAdapter(TrainingListAdapter trainingListAdapter) {
        trainingListRecyclerView.setAdapter(trainingListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false
        );
        trainingListRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout
                .VERTICAL));
        trainingListRecyclerView.setLayoutManager(layoutManager);
    }

    private void findViewsById() {
        trainingListRecyclerView = findViewById(R.id.trainingListRecyclerView);
        trainingListButton = findViewById(R.id.trainingListButton);
        trainingListToolBar = findViewById(R.id.trainingListToolBar);
        trainingListTextView = findViewById(R.id.trainingListTextView);
    }

    private void showProgressDialog() {
        loginProgressDialog = new ProgressDialog(this);
        loginProgressDialog.show();
        loginProgressDialog.setContentView(R.layout.progress_dialog);
        loginProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loginProgressDialog.setCancelable(false);
    }
}