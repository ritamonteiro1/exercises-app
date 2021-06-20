package com.example.leal.activity.exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leal.R;
import com.example.leal.constants.Constants;
import com.example.leal.utils.Utils;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class EditExerciseActivity extends AppCompatActivity {
    private Toolbar editExerciseToolBar;
    private EditText editExerciseEditTextMultiLine;
    private Button editExerciseCancelButton, editExerciseSaveButton;
    private CollectionReference exerciseListCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);
        findViewsById();
        String loggedUserEmail = retrieverLoggedUserEmail();
        String exerciseDocumentId = retrieverExerciseDocumentId();
        String trainingDocumentId = retrieverTrainingDocumentId();
        setupEditExerciseToolBar();
        setupSaveButton(exerciseDocumentId);
        setupCancelButton();
        setupExerciseListCollection(loggedUserEmail, trainingDocumentId);
    }

    private void setupExerciseListCollection(String loggedUserEmail, String trainingDocumentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        exerciseListCollection = db.collection(Constants.USERS_COLLECTION_PATH)
                .document(loggedUserEmail)
                .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                .document(trainingDocumentId)
                .collection(Constants.EXERCISE_LIST_FIELD_TRAINING_LIST);
    }

    private String retrieverTrainingDocumentId() {
        return getIntent().getStringExtra(Constants.TRAINING_DOCUMENT_ID);
    }

    private String retrieverExerciseDocumentId() {
        return getIntent().getStringExtra(Constants.EXERCISE_DOCUMENT_ID);
    }

    private void setupCancelButton() {
        editExerciseCancelButton.setOnClickListener(v -> Utils.createAlertDialogWithQuestion(getString(R.string.edit_exercise_message_cancel_edit_alert_dialog),
                this, (dialog, which) -> finish()
        ));
    }

    private void setupSaveButton(String exerciseDocumentId) {
        editExerciseSaveButton.setOnClickListener(v -> {
            String editedObservationExercise = editExerciseEditTextMultiLine.getText().toString();
            if (!editedObservationExercise.isEmpty()) {
                editExercise(exerciseDocumentId, editedObservationExercise);

            } else {
                Toast.makeText(
                        getApplicationContext(),
                        getString(R.string.edit_exercise_error_fill_the_field),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void editExercise(String exerciseDocumentId, String editedObservationExercise) {
        exerciseListCollection
                .document(exerciseDocumentId)
                .update(
                        Constants.OBSERVATION_FIELD_EXERCISE_LIST,
                        editedObservationExercise
                )
                .addOnSuccessListener(unused -> {
                    Toast.makeText(
                            getApplicationContext(),
                            getString(R.string.edit_exercise_successfull_change_description_training),
                            Toast.LENGTH_LONG
                    ).show();
                    finish();
                })
                .addOnFailureListener(e -> Utils.createErrorDialog(
                        getString(R.string.generic_error_try_again),
                        getString(R.string.alert_dialog_positive_message_ok),
                        this
                ));
    }

    private String retrieverLoggedUserEmail() {
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