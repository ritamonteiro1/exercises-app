package com.example.leal.activity;

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
import com.google.firebase.firestore.FirebaseFirestore;


public class EditTrainingActivity extends AppCompatActivity {
    private Toolbar editTrainingToolBar;
    private EditText editTrainingEditTextMultiLine;
    private Button editTrainingCancelButton, editTrainingSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_training);
        findViewsById();
        String loggedUserEmail = retrieverLoggedUserEmailFromTrainingListActivity();
        String trainingDocumentId = retrieverTrainingDocumentIdFromTrainingListActivity();
        setupEditTrainingToolBar();
        setupSaveButton(trainingDocumentId, loggedUserEmail);
        setupCancelButton();
    }

    private String retrieverLoggedUserEmailFromTrainingListActivity() {
        return getIntent().getStringExtra(Constants.LOGGED_USER_EMAIL);
    }

    private void setupCancelButton() {
        editTrainingCancelButton.setOnClickListener(v -> Utils.createAlertDialogWithQuestion(getString(R.string.edit_training_message_cancel_edit_alert_dialog), this,
                (dialog, which) -> finish()
        ));
    }

    private void setupSaveButton(String trainingDocumentId, String loggedUserEmail) {
        editTrainingSaveButton.setOnClickListener(v -> {
            String editedDescriptionTraining = editTrainingEditTextMultiLine.getText().toString();
            if (editedDescriptionTraining.isEmpty()) {
                Toast.makeText(
                        EditTrainingActivity.this,
                        getString(R.string.edit_training_error_fill_the_field),
                        Toast.LENGTH_LONG
                ).show();
            } else {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection(Constants.USERS_COLLECTION_PATH)
                        .document(loggedUserEmail)
                        .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                        .document(trainingDocumentId)
                        .update(Constants.DESCRIPTION_FIELD_TRAINING_LIST, editedDescriptionTraining
                        )
                        .addOnSuccessListener(unused -> Toast.makeText(
                                EditTrainingActivity.this,
                                getString(R.string.edit_training_successfull_change_description_training),
                                Toast.LENGTH_LONG
                        ).show())
                        .addOnFailureListener(e -> Toast.makeText(
                                EditTrainingActivity.this,
                                getString(R.string.generic_error_try_again),
                                Toast.LENGTH_LONG
                        ).show());
                finish();
            }});
    }

    private String retrieverTrainingDocumentIdFromTrainingListActivity() {
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