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
import com.example.leal.domains.training.TrainingResponse;
import com.example.leal.utils.Utils;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;


public class NewTrainingActivity extends AppCompatActivity {
    private Toolbar newTrainingToolBar;
    private EditText newTrainingEditTextMultiLine;
    private Button newTrainingCancelButton, newTrainingSaveButton;
    private CollectionReference trainingListCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_training);
        findViewsById();
        String loggedUserEmail = retrieverLoggedUserEmail();
        setupNewTrainingToolBar();
        setupTrainingListCollection(loggedUserEmail);
        setupNewTrainingCancelButton();
        setupNewTrainingSaveButton();
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

    private void setupNewTrainingSaveButton() {
        newTrainingSaveButton.setOnClickListener(v -> {
            String trainingDescription = newTrainingEditTextMultiLine.getText().toString();
            if (trainingDescription.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        getString(R.string.new_training_error_fill_the_field),
                        Toast.LENGTH_LONG
                ).show();
            } else {
                createNewTraining(trainingDescription);
            }
        });
    }

    private void createNewTraining(String trainingDescription) {
        Long id = System.currentTimeMillis();
        TrainingResponse trainingResponse = new TrainingResponse(id, trainingDescription,
                new Timestamp(new Date())
        );
        trainingListCollection
                .add(trainingResponse)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(
                            getApplicationContext(),
                            getString(R.string.new_training_successfully_create_training),
                            Toast.LENGTH_LONG
                    ).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Utils.createErrorDialog(getString(R.string.generic_error_try_again),
                                getString(R.string.alert_dialog_positive_message_ok), this
                        )
                );
    }

    private void findViewsById() {
        newTrainingToolBar = findViewById(R.id.newTrainingToolBar);
        newTrainingEditTextMultiLine = findViewById(R.id.newTrainingEditTextMultiLine);
        newTrainingCancelButton = findViewById(R.id.newExerciseCancelButton);
        newTrainingSaveButton = findViewById(R.id.newExerciseSaveButton);
    }
}