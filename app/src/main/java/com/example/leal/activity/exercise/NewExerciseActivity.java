package com.example.leal.activity.exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
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
import com.example.leal.domains.exercise.ExerciseRequest;
import com.example.leal.domains.exercise.ExerciseType;
import com.example.leal.utils.Utils;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NewExerciseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Toolbar newExerciseToolBar;
    private ImageView newExerciseImageView;
    private EditText newExerciseEditTextMultiLine;
    private Button newExerciseCancelButton, newExerciseSaveButton;
    private Spinner newExerciseSpinner;
    private ExerciseType selectedExerciseType;
    private CollectionReference exerciseListCollection;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);
        findViewsById();
        setupSpinner();
        String loggedUserEmail = retrieverLoggedUserEmail();
        String trainingDocumentId = retrieverTrainingDocumentId();
        setupNewExerciseToolBar();
        setupNewExerciseCancelButton();
        setupExerciseListCollection(loggedUserEmail, trainingDocumentId);
        setupNewExerciseSaveButton();
    }

    private void setupExerciseListCollection(String loggedUserEmail, String trainingDocumentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        exerciseListCollection = db.collection(Constants.USERS_COLLECTION_PATH)
                .document(loggedUserEmail)
                .collection(Constants.TRAINING_LIST_COLLECTION_PATH)
                .document(trainingDocumentId)
                .collection(Constants.EXERCISE_LIST_COLLECTION_PATH);
    }

    private void setupSpinner() {
        List<String> spinnerCategories = new ArrayList<>();
        spinnerCategories.add(ExerciseType.AEROBIC.getType());
        spinnerCategories.add(ExerciseType.BODYBUILDING.getType());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerCategories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newExerciseSpinner.setAdapter(adapter);
        newExerciseSpinner.setOnItemSelectedListener(this);
    }

    private String retrieverTrainingDocumentId() {
        return getIntent().getStringExtra(Constants.TRAINING_DOCUMENT_ID);
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

    private void setupNewExerciseCancelButton() {
        newExerciseCancelButton.setOnClickListener(v -> Utils.createAlertDialogWithQuestion(getString(R.string.new_exercise_message_cancel_create_alert_dialog),
                this, (dialog, which) -> finish()
        ));
    }

    private void setupNewExerciseSaveButton() {
        newExerciseSaveButton.setOnClickListener(v -> {
            String exerciseObservation = newExerciseEditTextMultiLine.getText().toString();
            if (!exerciseObservation.isEmpty()) {
                createNewExercise(exerciseObservation);
            } else {
                Toast.makeText(
                        NewExerciseActivity.this,
                        getString(R.string.new_exercise_error_fill_the_field),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void createNewExercise(String exerciseObservation) {
        progressDialog = Utils.showProgressDialog(this);
        Long id = System.currentTimeMillis();
        ExerciseRequest exerciseRequest = new ExerciseRequest(
                id,
                exerciseObservation,
                selectedExerciseType.getType(),
                selectedExerciseType.getUrlImage()
        );
        exerciseListCollection
                .add(exerciseRequest)
                .addOnSuccessListener(documentReference -> {
                    progressDialog.dismiss();
                    Toast.makeText(
                            this,
                            getString(R.string.new_exercise_successfully_create_training),
                            Toast.LENGTH_LONG
                    ).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    if (e instanceof FirebaseNetworkException) {
                        Utils.createErrorDialog(
                                getString(R.string.error_connection_fail),
                                getString(R.string.alert_dialog_positive_message_ok),
                                this
                        );
                    } else {
                        Utils.createErrorDialog(
                                getString(R.string.generic_error_try_again),
                                getString(R.string.alert_dialog_positive_message_ok),
                                this
                        );
                    }
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spinnerSelectedItem = parent.getItemAtPosition(position).toString();
        selectedExerciseType = ExerciseType.toExerciseType(spinnerSelectedItem);
        Glide.with(NewExerciseActivity.this)
                .load(selectedExerciseType.getUrlImage())
                .into(newExerciseImageView);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}