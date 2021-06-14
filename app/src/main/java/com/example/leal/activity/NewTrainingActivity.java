package com.example.leal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.leal.R;
import com.example.leal.utils.Utils;

public class NewTrainingActivity extends AppCompatActivity {
    private Toolbar newTrainingToolBar;
    private EditText newTrainingEditTextMultiLine;
    private Button newTrainingCancelButton, newTrainingSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_training);
        findViewsById();
        setupNewTrainingToolBar();
        setupNewTrainingCancelButton();
        setupNewTrainingSaveButton();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNewTrainingToolBar() {
        setSupportActionBar(newTrainingToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupNewTrainingCancelButton() {
        newTrainingCancelButton.setOnClickListener(v -> {
            Utils.createErrorDialogWithNegativeButton(getString(R.string.message_alert_dialog),
                    this);
        });
    }

    private void setupNewTrainingSaveButton() {

    }

    private void findViewsById() {
        newTrainingToolBar = findViewById(R.id.newTrainingToolBar);
        newTrainingEditTextMultiLine = findViewById(R.id.newTrainingEditTextMultiLine);
        newTrainingCancelButton = findViewById(R.id.newExerciseCancelButton);
        newTrainingSaveButton = findViewById(R.id.newExerciseSaveButton);
    }
}