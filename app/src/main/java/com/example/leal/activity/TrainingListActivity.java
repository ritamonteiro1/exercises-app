package com.example.leal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.leal.R;

import com.example.leal.adapter.TrainingListAdapter;

import com.example.leal.domains.Training;
import com.example.leal.firebase.TrainingListRepository;


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
        retrieverTrainingListFromFirebase();
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

    private void retrieverTrainingListFromFirebase() {
        List<Training> trainingList = TrainingListRepository.getTrainingListFromFirebase();
        TrainingListAdapter trainingListAdapter = new TrainingListAdapter(trainingList, this);
        setupRecyclerView(trainingListAdapter);
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