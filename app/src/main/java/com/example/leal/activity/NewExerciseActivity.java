package com.example.leal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.leal.R;
import com.example.leal.constants.Constants;
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
        String loggedUserEmail = retrieverLoggedUserEmailFromExerciseListActivity();
        setupNewExerciseToolBar();
        setupNewExerciseCancelButton();
        setupNewExerciseSaveButton(loggedUserEmail);
    }

    private String retrieverLoggedUserEmailFromExerciseListActivity() {
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

    private void setupNewExerciseCancelButton() {
        newExerciseCancelButton.setOnClickListener(v -> {
            Utils.createAlertDialogWithQuestion(getString(R.string.new_exercise_message_cancel_create_alert_dialog),
                    this, (dialog, which) -> finish()
            );
        });
    }

    private void setupNewExerciseSaveButton(String loggedUserEmail) {
    }

    private void setupNewExerciseToolBar() {
        setSupportActionBar(newExerciseToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.new_exercise_tool_bar_title));
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