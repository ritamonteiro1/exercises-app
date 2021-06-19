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
import com.example.leal.domains.TrainingResponse;
import com.example.leal.utils.Utils;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;


public class NewTrainingActivity extends AppCompatActivity {
    private Toolbar newTrainingToolBar;
    private EditText newTrainingEditTextMultiLine;
    private Button newTrainingCancelButton, newTrainingSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_training);
        findViewsById();
        String loggedUserEmail = retrieverLoggedUserEmailFromTrainingListActivity();
        setupNewTrainingToolBar();
        setupNewTrainingCancelButton();
        setupNewTrainingSaveButton(loggedUserEmail);
    }

    private String retrieverLoggedUserEmailFromTrainingListActivity() {
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

    private void setupNewTrainingToolBar() {
        setSupportActionBar(newTrainingToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.new_training_tool_bar_title));
        }
    }

    private void setupNewTrainingCancelButton() {
        newTrainingCancelButton.setOnClickListener(v -> Utils.createAlertDialogWithQuestion(
                getString(R.string.new_training_message_cancel_create_alert_dialog),
                this,
                (dialog, which) -> finish()
        ));
    }

    private void setupNewTrainingSaveButton(String loggedUserEmail) {
        newTrainingSaveButton.setOnClickListener(v -> {
            String trainingDescription = newTrainingEditTextMultiLine.getText().toString();
            if (trainingDescription.isEmpty()) {
                Toast.makeText(
                        NewTrainingActivity.this,
                        getString(R.string.new_training_error_fill_the_field),
                        Toast.LENGTH_LONG
                ).show();
            } else {
                Long id = System.currentTimeMillis();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                TrainingResponse trainingResponse = new TrainingResponse(id, trainingDescription,
                        new Timestamp(new Date())
                );
                db.collection(Constants.USERS_COLLECTION_PATH)
                        .document(loggedUserEmail)
                        .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                        .add(trainingResponse)
                        .addOnSuccessListener(documentReference -> Toast.makeText(
                                getApplicationContext(),
                                getString(R.string.new_training_successfully_create_training),
                                Toast.LENGTH_LONG
                        ).show())
                        .addOnFailureListener(e -> Toast.makeText(
                                this,
                                getString(R.string.generic_error_try_again),
                                Toast.LENGTH_LONG
                        ).show());
                finish();
            }
        });
    }

    private void findViewsById() {
        newTrainingToolBar = findViewById(R.id.newTrainingToolBar);
        newTrainingEditTextMultiLine = findViewById(R.id.newTrainingEditTextMultiLine);
        newTrainingCancelButton = findViewById(R.id.newExerciseCancelButton);
        newTrainingSaveButton = findViewById(R.id.newExerciseSaveButton);
    }
}