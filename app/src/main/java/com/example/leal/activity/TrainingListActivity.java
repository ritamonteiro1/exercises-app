package com.example.leal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.LinearLayout;

import com.example.leal.R;

import com.example.leal.adapter.TrainingListAdapter;

import com.google.firebase.firestore.FirebaseFirestore;


public class TrainingListActivity extends AppCompatActivity {
    private RecyclerView trainingListRecyclerView;
    private Button trainingListButton;
    private Toolbar trainingListToolBar;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);
        db = FirebaseFirestore.getInstance();
        findViewsById();
        getTrainingListFromFirebase();
        setupTrainingListButton();
        setupTrainingListToolBar();

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

    private void getTrainingListFromFirebase() {

    }

    private void setupRecyclerView(TrainingListAdapter trainingListAdapter) {
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