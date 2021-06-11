package com.example.leal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.leal.R;
import com.example.leal.utils.Utils;

public class NewExerciseActivity extends AppCompatActivity {
    private Toolbar newExerciseToolBar;
    private ImageView newExerciseImageView;
    private EditText newExerciseEditTextMultiLine;
    private Button newExerciseCancelButton, newExerciseSaveButton;
    private Spinner newExerciseSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);
        findViewsById();
        setupNewExerciseToolBar();
        setupNewExerciseCancelButton();
        setupNewExerciseSaveButton();
    }

    private void setupNewExerciseCancelButton() {
        newExerciseCancelButton.setOnClickListener(v -> {
            Utils.createErrorDialogWithNegativeButton(getString(R.string.message_alert_dialog),
                    this);
        });
    }

    private void setupNewExerciseSaveButton() {
    }

    private void setupNewExerciseToolBar() {
        setSupportActionBar(newExerciseToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void findViewsById() {
        newExerciseToolBar = findViewById(R.id.newExerciseToolBar);
        newExerciseImageView = findViewById(R.id.newExerciseImageView);
        newExerciseEditTextMultiLine = findViewById(R.id.newExerciseEditTextMultiLine);
        newExerciseCancelButton = findViewById(R.id.newExerciseCancelButton);
        newExerciseSaveButton = findViewById(R.id.newExerciseSaveButton);
        newExerciseSpinner = findViewById(R.id.newExerciseSpinner);
    }
}