package com.example.leal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.leal.R;
import com.example.leal.constants.Constants;
import com.example.leal.domains.User;

public class EditExerciseActivity extends AppCompatActivity {
    private Toolbar editExerciseToolBar;
    private EditText editExerciseEditTextMultiLine;
    private Button editExerciseCancelButton, editExerciseSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);
        findViewsById();
        User user = retrieverUserFromLoginActivity();
        setupEditExerciseToolBar();
        setupSaveButton(user);
    }

    private void setupSaveButton(User user) {

    }

    private User retrieverUserFromLoginActivity() {
        return (User) getIntent().getSerializableExtra(Constants.USER);
    }

    private void findViewsById() {
        editExerciseToolBar = findViewById(R.id.editExerciseToolBar);
        editExerciseEditTextMultiLine = findViewById(R.id.editExerciseEditTextMultiLine);
        editExerciseCancelButton = findViewById(R.id.editExerciseCancelButton);
        editExerciseSaveButton = findViewById(R.id.editExerciseSaveButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupEditExerciseToolBar() {
        setSupportActionBar(editExerciseToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.edit_exercise));
        }
    }
}