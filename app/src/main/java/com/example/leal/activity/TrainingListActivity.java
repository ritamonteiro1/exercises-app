package com.example.leal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class TrainingListActivity extends AppCompatActivity {
    private RecyclerView trainingListRecyclerView;
    private Button trainingListButton;
    private Toolbar trainingListToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);
        findViewsById();
        getTrainingListFromFirebase();
        setupTrainingListButton();
        setupTrainingListToolBar();
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
        trainingListButton.setOnClickListener(v -> {
            moveToNewTrainingActivity();
        });
    }

    private void moveToNewTrainingActivity() {
        Intent intent = new Intent(this, NewTrainingActivity.class);
        startActivity(intent);
    }

    public void getTrainingListFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.USERS_COLLECTION_PATH)
                .document(Constants.EMAIL_DOCUMENT_PATH)
                .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        ArrayList<Training> trainingList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Training training = document.toObject(Training.class);
                            training.setDocumentId(document.getId());
                            trainingList.add(training);
                        }
                        setupRecyclerView(trainingList);
                    }
                });
    }

    private void setupRecyclerView(List<Training> trainingList) {
        TrainingListAdapter trainingListAdapter = new TrainingListAdapter(trainingList, this);

        trainingListRecyclerView.setAdapter(trainingListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        trainingListRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout
                .VERTICAL));
        trainingListRecyclerView.setLayoutManager(layoutManager);
    }

    private void findViewsById() {
        trainingListRecyclerView = findViewById(R.id.trainingListRecyclerView);
        trainingListButton = findViewById(R.id.trainingListButton);
        trainingListToolBar = findViewById(R.id.trainingListToolBar);
    }
}