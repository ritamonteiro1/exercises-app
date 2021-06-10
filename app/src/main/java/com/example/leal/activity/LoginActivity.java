package com.example.leal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.leal.R;
import com.example.leal.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmailEditText, loginPasswordEditText;
    private TextInputLayout loginEmailTextInputLayout, loginPasswordTextInputLayout;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewsById();
        setupLoginButton();
    }

    private void setupLoginButton() {
        loginButton.setOnClickListener(v -> {
            boolean isValidEmail = Utils.isValidEmail(loginEmailEditText, loginEmailTextInputLayout,
                    this);
            boolean isEmptyPassword = Utils.isEmptyField(loginPasswordTextInputLayout,
                    loginPasswordEditText, this);
            if (!isValidEmail || isEmptyPassword) return;

        });
    }

    private void findViewsById() {
        loginEmailEditText = findViewById(R.id.loginEmailEditText);
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText);
        loginEmailTextInputLayout = findViewById(R.id.loginEmailTextInputLayout);
        loginPasswordTextInputLayout = findViewById(R.id.loginPasswordTextInputLayout);
        loginButton = findViewById(R.id.loginButton);
    }
}