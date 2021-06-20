package com.example.leal.activity.training;

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
import com.example.leal.activity.exercise.ExerciseListActivity;
import com.example.leal.adapter.TrainingListAdapter;
import com.example.leal.click.listener.training.click.listener.OnTrainingDeleteClickListener;
import com.example.leal.click.listener.training.click.listener.OnTrainingEditClickListener;
import com.example.leal.click.listener.training.click.listener.OnTrainingItemClickListener;
import com.example.leal.constants.Constants;
import com.example.leal.domains.training.Training;
import com.example.leal.domains.training.TrainingResponse;
import com.example.leal.utils.Utils;

import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class TrainingListActivity extends AppCompatActivity {
    private RecyclerView trainingListRecyclerView;
    private Button trainingListButton;
    private Toolbar trainingListToolBar;
    private TextView trainingListTextView;
    private ProgressDialog progressDialog;
    private String loggedUserEmail;
    private CollectionReference trainingListCollection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);
        findViewsById();
        loggedUserEmail = retrieverLoggedUserEmail();
        setupTrainingListCollection();
        setupTrainingListButton(loggedUserEmail);
        setupTrainingListToolBar();
    }

    private void setupTrainingListCollection() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        trainingListCollection =
                db.collection(Constants.USERS_COLLECTION_PATH).
                        document(loggedUserEmail).collection(Constants.TRAINING_LIST_COLLECTION_PATH);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog = Utils.showProgressDialog(this);
        getTrainingListFromFirebase(loggedUserEmail);
    }

    private String retrieverLoggedUserEmail() {
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
        trainingListCollection
                .orderBy(Constants.TRAINING_TIMESTAMP)
                .get()
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    trainingListTextView.setVisibility(View.GONE);
                    if (task.isSuccessful() && task.getResult() != null) {
                        successfullyGetTrainingListFromFirebase(loggedUserEmail, task.getResult());
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

    private void successfullyGetTrainingListFromFirebase(String loggedUserEmail,
                                                         QuerySnapshot result) {
        trainingListRecyclerView.setVisibility(View.VISIBLE);
        ArrayList<Training> trainingList = new ArrayList<>();
        if (result != null && !result.isEmpty()) {
            for (QueryDocumentSnapshot document : result) {
                TrainingResponse trainingResponse = document.toObject(TrainingResponse.class);
                trainingResponse.setDocumentId(document.getId());
                Training training = new Training(trainingResponse.getId(),
                        trainingResponse.getDescription(), trainingResponse.getDate(), trainingResponse.getDocumentId());
                trainingList.add(training);
            }
        } else {
            trainingListTextView.setText(getString(R.string.training_list_empty_message));
            trainingListTextView.setVisibility(View.VISIBLE);
        }
        setupRecyclerView(trainingList, loggedUserEmail);
    }

    private void setupRecyclerView(List<Training> trainingList,
                                   String loggedUserEmail) {
        TrainingListAdapter trainingListAdapter = new TrainingListAdapter(
                trainingList,
                this,
                setupDeleteTrainingClickListener(loggedUserEmail),
                setupEditTrainingClickListener(loggedUserEmail),
                setupItemTrainingClickListener(loggedUserEmail)
        );
        setupAdapter(trainingListAdapter);
    }

    @NotNull
    private OnTrainingItemClickListener setupItemTrainingClickListener(String loggedUserEmail) {
        return (trainingNumberId,
                trainingDocumentId) -> {
            Intent intent = new Intent(TrainingListActivity.this, ExerciseListActivity.class);
            intent.putExtra(Constants.TRAINING_NUMBER_ID, trainingNumberId);
            intent.putExtra(Constants.TRAINING_DOCUMENT_ID, trainingDocumentId);
            intent.putExtra(Constants.LOGGED_USER_EMAIL, loggedUserEmail);
            startActivity(intent);
        };
    }

    @NotNull
    private OnTrainingEditClickListener setupEditTrainingClickListener(String loggedUserEmail) {
        return trainingDocumentId -> {
            Intent intent = new Intent(TrainingListActivity.this, EditTrainingActivity.class);
            intent.putExtra(Constants.TRAINING_DOCUMENT_ID, trainingDocumentId);
            intent.putExtra(Constants.LOGGED_USER_EMAIL, loggedUserEmail);
            startActivity(intent);
        };
    }

    @NotNull
    private OnTrainingDeleteClickListener setupDeleteTrainingClickListener(String loggedUserEmail) {
        return training -> Utils.createAlertDialogWithQuestion(
                getString(R.string.training_list_delete_question_training),
                this,
                (dialog, which) -> deleteTraining(loggedUserEmail, training)
        );
    }

    private void deleteTraining(String loggedUserEmail, Training training) {
        trainingListCollection
                .document(training.getDocumentId())
                .delete()
                .addOnSuccessListener(
                        unused -> {
                            Toast.makeText(
                                    getApplicationContext(),
                                    getString(R.string.training_list_successfully_deleted_training),
                                    Toast.LENGTH_LONG
                            ).show();
                            getTrainingListFromFirebase(loggedUserEmail);
                        })
                .addOnFailureListener(e -> Utils.createErrorDialog(getString(R.string.training_list_error_deleted_training),
                        getString(R.string.alert_dialog_positive_message_ok), this
                ));
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
}