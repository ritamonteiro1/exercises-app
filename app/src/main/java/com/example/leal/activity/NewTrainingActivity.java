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
import com.example.leal.domains.Training;
import com.example.leal.domains.User;
import com.example.leal.utils.Utils;
import com.google.firebase.firestore.FirebaseFirestore;


public class NewTrainingActivity extends AppCompatActivity {
    private Toolbar newTrainingToolBar;
    private EditText newTrainingEditTextMultiLine;
    private Button newTrainingCancelButton, newTrainingSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_training);
        findViewsById();
        User user = retrieverUserFromLoginActivity();
        setupNewTrainingToolBar();
        setupNewTrainingCancelButton();
        setupNewTrainingSaveButton(user);
    }

    private User retrieverUserFromLoginActivity() {
        return (User) getIntent().getSerializableExtra(Constants.USER);
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
            getSupportActionBar().setTitle(getString(R.string.create_new_training));
        }
    }

    private void setupNewTrainingCancelButton() {
        newTrainingCancelButton.setOnClickListener(v -> Utils.createAlertDialogWithQuestion(
                getString(R.string.message_alert_dialog),
                this,
                (dialog, which) -> finish()
        ));
    }

    private void setupNewTrainingSaveButton(User user) {
        newTrainingSaveButton.setOnClickListener(v -> {
            String description = newTrainingEditTextMultiLine.getText().toString();
            Long id = System.currentTimeMillis();
            //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Training training = new Training();
            training.setId(id);
            training.setDescription(description);

            db.collection(Constants.USERS_COLLECTION_PATH)
                    .document(Constants.EMAIL_DOCUMENT_PATH)
                    .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                    .add(training)
                    .addOnSuccessListener(documentReference -> Toast.makeText(this,
                            getString(R.string.created_training), Toast.LENGTH_LONG
                    ).show())
                    .addOnFailureListener(e -> Toast.makeText(this,
                            getString(R.string.ocurred_error),
                            Toast.LENGTH_LONG
                    ).show());
        });
    }

    private void findViewsById() {
        newTrainingToolBar = findViewById(R.id.newTrainingToolBar);
        newTrainingEditTextMultiLine = findViewById(R.id.newTrainingEditTextMultiLine);
        newTrainingCancelButton = findViewById(R.id.newExerciseCancelButton);
        newTrainingSaveButton = findViewById(R.id.newExerciseSaveButton);
    }
}