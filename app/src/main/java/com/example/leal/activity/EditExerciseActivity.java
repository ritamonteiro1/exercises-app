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
import com.example.leal.utils.Utils;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditExerciseActivity extends AppCompatActivity {
    private Toolbar editExerciseToolBar;
    private EditText editExerciseEditTextMultiLine;
    private Button editExerciseCancelButton, editExerciseSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);
        findViewsById();
        String loggedEmailUser = retrieverLoggedUserEmailFromExerciseListActivity();
        String exerciseDocumentId = retrieverDataFromExerciseListActivity();
        setupEditExerciseToolBar();
        setupSaveButton(loggedEmailUser, exerciseDocumentId);
        setupCancelButton();
    }

    private String retrieverDataFromExerciseListActivity() {
        return getIntent().getStringExtra(Constants.EXERCISE_DOCUMENT_ID);
    }

    private void setupCancelButton() {
        editExerciseCancelButton.setOnClickListener(v -> {
            Utils.createAlertDialogWithQuestion(getString(R.string.edit_exercise_message_cancel_edit_alert_dialog),
                    this, (dialog, which) -> finish());
        });
    }

    private void setupSaveButton(String loggedUserEmail, String exerciseDocumentId) {
//        editExerciseSaveButton.setOnClickListener(v -> {
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            db.collection(Constants.USERS_COLLECTION_PATH)
//                    .document(loggedUserEmail)
//                    .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
//                    .
//        });
    }

    private String retrieverLoggedUserEmailFromExerciseListActivity() {
        return getIntent().getStringExtra(Constants.LOGGED_USER_EMAIL);
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
            getSupportActionBar().setTitle(getString(R.string.edit_exercise_tool_bar_title));
        }
    }
}