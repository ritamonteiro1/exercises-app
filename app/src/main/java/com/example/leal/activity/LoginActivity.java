package com.example.leal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.leal.R;
import com.example.leal.constants.Constants;
import com.example.leal.domains.User;
import com.example.leal.utils.Utils;


import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;


public class LoginActivity extends AppCompatActivity {
    private EditText loginEmailEditText, loginPasswordEditText;
    private TextInputLayout loginEmailTextInputLayout, loginPasswordTextInputLayout;
    private Button loginButton;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog loginProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        findViewsById();
        setupLoginButton();
    }

    private void setupLoginButton() {
        loginButton.setOnClickListener(v -> {
            boolean isValidEmail = Utils.isValidEmail(loginEmailEditText, loginEmailTextInputLayout,
                    this
            );
            boolean isEmptyPassword = Utils.isEmptyField(loginPasswordTextInputLayout,
                    loginPasswordEditText, this
            );
            if (!isValidEmail || isEmptyPassword) return;
            User user = new User(
                    loginEmailEditText.getText().toString(),
                    loginPasswordEditText.getText().toString()
            );
            showProgressDialog();
            checkRegisteredUser(user);
        });
    }

    private void checkRegisteredUser(User user) {
        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).
                addOnCompleteListener(this, task -> {
                    loginProgressDialog.dismiss();
                    if (task.isSuccessful()) {
                        moveToTrainingActivity(user);
                    } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                        loginEmailTextInputLayout.setError(getString(R.string.unauthenticated_user));
                        loginPasswordTextInputLayout.setError(Constants.BLANK_SPACE);
                    } else if (task.getException() instanceof FirebaseNetworkException) {
                        Utils.createErrorDialog(getString(R.string.connection_fail_error),
                                getString(R.string.alert_dialog_positive_message_ok), this
                        );
                    } else {
                        Utils.createErrorDialog(getString(R.string.ocurred_error),
                                getString(R.string.alert_dialog_positive_message_ok), this
                        );
                    }
                });
    }

    private void showProgressDialog() {
        loginProgressDialog = new ProgressDialog(this);
        loginProgressDialog.show();
        loginProgressDialog.setContentView(R.layout.progress_dialog);
        loginProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loginProgressDialog.setCancelable(false);
    }

    private void moveToTrainingActivity(User user) {
        sendUserToNewTrainingActivity(user);
        sendUserToNewExerciseActivity(user);
        sendUserToExerciseListActivity(user);
        sendUserToEditTrainingActivity(user);
        sendUserToEditExerciseActivity(user);
        Intent intent = new Intent(this, TrainingListActivity.class);
        intent.putExtra(Constants.USER, user);
        startActivity(intent);
        finish();
    }

    private void sendUserToEditExerciseActivity(User user) {
        Intent intent = new Intent(this, EditExerciseActivity.class);
        intent.putExtra(Constants.USER, user);
    }

    private void sendUserToEditTrainingActivity(User user) {
        Intent intent = new Intent(this, EditTrainingActivity.class);
        intent.putExtra(Constants.USER, user);
    }

    private void sendUserToExerciseListActivity(User user) {
        Intent intent = new Intent(this, ExerciseListActivity.class);
        intent.putExtra(Constants.USER, user);
    }

    private void sendUserToNewExerciseActivity(User user) {
        Intent intent = new Intent(this, NewExerciseActivity.class);
        intent.putExtra(Constants.USER, user);
    }

    private void sendUserToNewTrainingActivity(User user) {
        Intent intent = new Intent(this, NewTrainingActivity.class);
        intent.putExtra(Constants.USER, user);
    }

    private void findViewsById() {
        loginEmailEditText = findViewById(R.id.loginEmailEditText);
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText);
        loginEmailTextInputLayout = findViewById(R.id.loginEmailTextInputLayout);
        loginPasswordTextInputLayout = findViewById(R.id.loginPasswordTextInputLayout);
        loginButton = findViewById(R.id.loginButton);
    }
}