package com.example.leal.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.leal.R;
import com.example.leal.activity.training.TrainingListActivity;
import com.example.leal.constants.Constants;
import com.example.leal.domains.user.UserRequest;
import com.example.leal.utils.Utils;


import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;


public class LoginActivity extends AppCompatActivity {
    private EditText loginEmailEditText, loginPasswordEditText;
    private TextInputLayout loginEmailTextInputLayout, loginPasswordTextInputLayout;
    private Button loginButton;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

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
            UserRequest userRequest = new UserRequest(
                    loginEmailEditText.getText().toString(),
                    loginPasswordEditText.getText().toString()
            );
            progressDialog = Utils.showProgressDialog(this);
            checkRegisteredUser(userRequest);
        });
    }

    private void checkRegisteredUser(UserRequest userRequest) {
        firebaseAuth.signInWithEmailAndPassword(userRequest.getEmail(), userRequest.getPassword()).
                addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        moveToTrainingActivity(userRequest.getEmail());
                    } else if (task.getException() instanceof FirebaseAuthInvalidUserException ||
                            task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        loginEmailTextInputLayout.setError(Constants.BLANK_SPACE);
                        loginPasswordTextInputLayout.setError(getString(R.string.login_error_unauthenticated_user));
                    } else if (task.getException() instanceof FirebaseNetworkException) {
                        Utils.createErrorDialog(getString(R.string.error_connection_fail),
                                getString(R.string.alert_dialog_positive_message_ok), this
                        );
                    } else {
                        Utils.createErrorDialog(getString(R.string.generic_error_try_again),
                                getString(R.string.alert_dialog_positive_message_ok), this
                        );
                    }
                });
    }

    private void moveToTrainingActivity(String loggedUserEmail) {
        Intent intent = new Intent(this, TrainingListActivity.class);
        intent.putExtra(Constants.LOGGED_USER_EMAIL, loggedUserEmail);
        startActivity(intent);
        finish();
    }

    private void findViewsById() {
        loginEmailEditText = findViewById(R.id.loginEmailEditText);
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText);
        loginEmailTextInputLayout = findViewById(R.id.loginEmailTextInputLayout);
        loginPasswordTextInputLayout = findViewById(R.id.loginPasswordTextInputLayout);
        loginButton = findViewById(R.id.loginButton);
    }
}