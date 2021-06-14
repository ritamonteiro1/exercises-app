package com.example.leal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.leal.R;

public class ExerciseListActivity extends AppCompatActivity {
    private Toolbar exerciseListToolBar;
    private RecyclerView exerciseListRecyclerView;
    private Button exerciseListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        findViewsById();
        setupExerciseListToolBar();
        setupExerciseListButton();
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
        exerciseListButton.setOnClickListener(v -> {
            moveToNewExerciseActivity();
        });
    }

    private void moveToNewExerciseActivity() {
        Intent intent = new Intent(this, NewExerciseActivity.class);
        startActivity(intent);
    }

    private void setupExerciseListToolBar() {
        setSupportActionBar(exerciseListToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void findViewsById() {
        exerciseListToolBar = findViewById(R.id.exerciseListToolBar);
        exerciseListRecyclerView = findViewById(R.id.exerciseListRecyclerView);
        exerciseListButton = findViewById(R.id.exerciseListButton);
    }
}