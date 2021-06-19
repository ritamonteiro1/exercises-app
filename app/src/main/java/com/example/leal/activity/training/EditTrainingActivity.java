package com.example.leal.activity.training;

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


public class EditTrainingActivity extends AppCompatActivity {
    private Toolbar editTrainingToolBar;
    private EditText editTrainingEditTextMultiLine;
    private Button editTrainingCancelButton, editTrainingSaveButton;
    private CollectionReference trainingListCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_training);
        findViewsById();
        String loggedUserEmail = retrieverLoggedUserEmail();
        String trainingDocumentId = retrieverTrainingDocumentId();
        setupEditTrainingToolBar();
        setupSaveButton(trainingDocumentId);
        setupTrainingListCollection(loggedUserEmail);
        setupCancelButton();
    }

    private void setupTrainingListCollection(String loggedUserEmail) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        trainingListCollection =
                db.collection(Constants.USERS_COLLECTION_PATH).
                        document(loggedUserEmail).collection(Constants.TRAINING_LIST_COLLECTION_PATH);
    }

    private String retrieverLoggedUserEmail() {
        return getIntent().getStringExtra(Constants.LOGGED_USER_EMAIL);
    }

    private void setupCancelButton() {
        editTrainingCancelButton.setOnClickListener(v -> Utils.createAlertDialogWithQuestion(getString(R.string.edit_training_message_cancel_edit_alert_dialog), this,
                (dialog, which) -> finish()
        ));
    }

    private void setupSaveButton(String trainingDocumentId) {
        editTrainingSaveButton.setOnClickListener(v -> {
            String editedDescriptionTraining = editTrainingEditTextMultiLine.getText().toString();
            if (editedDescriptionTraining.isEmpty()) {
                Toast.makeText(
                        EditTrainingActivity.this,
                        getString(R.string.edit_training_error_fill_the_field),
                        Toast.LENGTH_LONG
                ).show();
            } else {
                editTraining(trainingDocumentId, editedDescriptionTraining);
            }
        });
    }

    private void editTraining(String trainingDocumentId, String editedDescriptionTraining) {
        trainingListCollection
                .document(trainingDocumentId)
                .update(Constants.DESCRIPTION_FIELD_TRAINING_LIST, editedDescriptionTraining
                )
                .addOnSuccessListener(unused -> {
                    Toast.makeText(
                            EditTrainingActivity.this,
                            getString(R.string.edit_training_successfull_change_description_training),
                            Toast.LENGTH_LONG
                    ).show();
                    finish();
                })
                .addOnFailureListener(e -> Utils.createErrorDialog(getString(R.string.generic_error_try_again),
                        getString(R.string.alert_dialog_positive_message_ok), this
                ));
    }

    private String retrieverTrainingDocumentId() {
        return getIntent().getStringExtra(Constants.TRAINING_DOCUMENT_ID);
    }

    private void findViewsById() {
        editTrainingToolBar = findViewById(R.id.editTrainingToolBar);
        editTrainingEditTextMultiLine = findViewById(R.id.editTrainingEditTextMultiLine);
        editTrainingCancelButton = findViewById(R.id.editTrainingCancelButton);
        editTrainingSaveButton = findViewById(R.id.editTrainingSaveButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupEditTrainingToolBar() {
        setSupportActionBar(editTrainingToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.edit_training_tool_bar_title));
        }
    }
}