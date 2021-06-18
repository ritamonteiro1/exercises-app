package com.example.leal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.leal.R;
import com.example.leal.constants.Constants;
import com.example.leal.domains.ExerciseRequest;
import com.example.leal.domains.ExerciseType;
import com.example.leal.utils.Utils;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.List;

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
        setupSpinner();
        String loggedUserEmail = retrieverLoggedUserEmailFromExerciseListActivity();
        String trainingDocumentId = retrieverTrainingDocumentIdFromExerciseListActivity();
        String aerobicTrainingTypeImageUrl = Constants.AEROBIC_PHOTO_URL_FROM_STORAGE;
        String bodybuildingTrainingTypeImageUrl = Constants.BODYBUILDING_PHOTO_URL_FROM_STORAGE;
        setupNewExerciseToolBar();
        setupNewExerciseCancelButton();
        setupNewExerciseSaveButton(loggedUserEmail, trainingDocumentId,
                aerobicTrainingTypeImageUrl, bodybuildingTrainingTypeImageUrl
        );
    }

    private void setupSpinner() {
        List<String> spinnerCategories = new ArrayList<String>();
        spinnerCategories.add(ExerciseType.AEROBIC.getDescription());
        spinnerCategories.add(ExerciseType.BODYBUILDING.getDescription());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerCategories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newExerciseSpinner.setAdapter(adapter);
    }

    private String retrieverTrainingDocumentIdFromExerciseListActivity() {
        return getIntent().getStringExtra(Constants.TRAINING_DOCUMENT_ID);
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

    private void setupNewExerciseSaveButton(String loggedUserEmail, String trainingDocumentId,
                                            String aerobicTrainingTypeImageUrl,
                                            String bodybuildingTrainingTypeImageUrl) {
        newExerciseSaveButton.setOnClickListener(v -> {
            String exerciseObservation = newExerciseEditTextMultiLine.getText().toString();
            newExerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position,
                                           long id) {
                    String spinnerSelectedItem = newExerciseSpinner.getSelectedItem().toString();
                    String selectedExerciseType;
                    if (spinnerSelectedItem.equals(ExerciseType.AEROBIC.getDescription())) {
                        selectedExerciseType = aerobicTrainingTypeImageUrl;
                    } else {
                        selectedExerciseType = bodybuildingTrainingTypeImageUrl;
                    }
                    Glide.with(NewExerciseActivity.this)
                            .load(selectedExerciseType)
                            .into(newExerciseImageView);

                    newExerciseImageView.setVisibility(View.VISIBLE);
                    if (!exerciseObservation.isEmpty()) {
                        createNewExercise(exerciseObservation, selectedExerciseType,
                                spinnerSelectedItem, loggedUserEmail, trainingDocumentId
                        );
                    } else {
                        Toast.makeText(
                                NewExerciseActivity.this,
                                getString(R.string.new_exercise_error_fill_the_field),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        });
    }

    private void createNewExercise(String exerciseObservation, String selectedExerciseType,
                                   String spinnerSelectedItem, String loggedUserEmail,
                                   String trainingDocumentId) {
        Long id = System.currentTimeMillis();
        ExerciseRequest exerciseRequest = new ExerciseRequest(
                id,
                exerciseObservation,
                ExerciseType.toExerciseType(spinnerSelectedItem),
                selectedExerciseType
        );
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.USERS_COLLECTION_PATH)
                .document(loggedUserEmail)
                .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                .document(trainingDocumentId)
                .collection(Constants.EXERCISE_LIST_COLLECTION_PATH)
                .add(exerciseRequest)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(
                            this,
                            getString(R.string.new_exercise_successfully_create_training),
                            Toast.LENGTH_LONG
                    ).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(
                            this,
                            getString(R.string.generic_error_try_again),
                            Toast.LENGTH_LONG
                    ).show();
                });
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