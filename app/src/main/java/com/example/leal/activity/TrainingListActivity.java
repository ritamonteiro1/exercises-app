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
import com.example.leal.domains.Training;
import com.example.leal.domains.User;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);
        findViewsById();
        User user = retrieverUserFromLoginActivity();
        showProgressDialog();
        getTrainingListFromFirebase(user);
        setupTrainingListButton();
        setupTrainingListToolBar();
    }

    private User retrieverUserFromLoginActivity() {
        return (User) getIntent().getSerializableExtra(Constants.USER);
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
            getSupportActionBar().setTitle(getString(R.string.trainings));
        }
    }

    private void setupTrainingListButton() {
        trainingListButton.setOnClickListener(v -> moveToNewTrainingActivity());
    }

    private void moveToNewTrainingActivity() {
        Intent intent = new Intent(this, NewTrainingActivity.class);
        startActivity(intent);
    }

    public void getTrainingListFromFirebase(User user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.USERS_COLLECTION_PATH)
                .document(user.getEmail())
                .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        loginProgressDialog.dismiss();
                        trainingListRecyclerView.setVisibility(View.VISIBLE);
                        ArrayList<Training> trainingList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Training training = document.toObject(Training.class);
                            training.setDocumentId(document.getId());
                            trainingList.add(training);
                        }
                        setupRecyclerView(trainingList, user);
                    } else if (task.getException() instanceof FirebaseNetworkException) {
                        loginProgressDialog.dismiss();
                        trainingListTextView.setVisibility(View.VISIBLE);
                    } else {
                        loginProgressDialog.dismiss();
                        trainingListTextView.setText(getString(R.string.error_training_list));
                        trainingListTextView.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void setupRecyclerView(List<Training> trainingList, User user) {
        TrainingListAdapter trainingListAdapter = new TrainingListAdapter(
                trainingList,
                this,
                training -> Utils.createAlertDialogWithQuestion(
                        getString(R.string.item_training_delete_message),
                        this,
                        (dialog, which) -> deleteTraining(user, training)
                )
        );
        setupAdapter(trainingListAdapter);
    }

    private void deleteTraining(User user, Training training) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.USERS_COLLECTION_PATH)
                .document(user.getEmail())
                .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                .document(training.getDocumentId())
                .delete()
                .addOnSuccessListener(unused -> Toast.makeText(
                        TrainingListActivity.this,
                        getString(R.string.deleted_training),
                        Toast.LENGTH_LONG
                ).show())
                .addOnFailureListener(e -> Toast.makeText(
                        TrainingListActivity.this,
                        getString(R.string.error_deleted_training),
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