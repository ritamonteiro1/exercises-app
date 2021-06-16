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
import com.example.leal.domains.User;
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
        User user = retrieverUserFromLoginActivity();
        setupEditTrainingToolBar();
        String trainingDocumentId = retrieverDataFromTrainingListActivity();
        setupSaveButton(trainingDocumentId, user);
        setupCancelButton();
    }

    private User retrieverUserFromLoginActivity() {
        return (User) getIntent().getSerializableExtra(Constants.USER);
    }

    private void setupCancelButton() {
        editTrainingCancelButton.setOnClickListener(v -> Utils.createAlertDialogWithQuestion(getString(R.string.message_alert_dialog), this,
                (dialog, which) -> finish()
        ));
    }

    private void setupSaveButton(String trainingDocumentId, User user) {
        editTrainingSaveButton.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(Constants.USERS_COLLECTION_PATH)
                    .document(Constants.EMAIL_DOCUMENT_PATH)
                    .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                    .document(trainingDocumentId)
                    .update(Constants.DESCRIPTION_FIELD_TRAINING_LIST,
                            editTrainingEditTextMultiLine.getText().toString())
                    .addOnSuccessListener(unused -> Toast.makeText(
                            EditTrainingActivity.this,
                            getString(R.string.edited_description_training),
                            Toast.LENGTH_LONG
                    ).show())
                    .addOnFailureListener(e -> Toast.makeText(
                            EditTrainingActivity.this,
                            getString(R.string.error_edited_description_training),
                            Toast.LENGTH_LONG
                    ).show());
        });
    }

    private String retrieverDataFromTrainingListActivity() {
        return getIntent().getStringExtra(Constants.TRAINING_DETAILS);
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
            getSupportActionBar().setTitle(getString(R.string.edit_training));
        }
    }
}