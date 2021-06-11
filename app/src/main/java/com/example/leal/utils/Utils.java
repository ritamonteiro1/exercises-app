package com.example.leal.utils;


import android.content.Context;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.leal.R;
import com.example.leal.constants.Constants;
import com.google.android.material.textfield.TextInputLayout;

import static android.util.Patterns.EMAIL_ADDRESS;

public class Utils {
    public static boolean isEmptyField(TextInputLayout textInputLayout, EditText editText,
                                       Context context) {
        String value = editText.getText().toString();
        if (value.isEmpty()) {
            textInputLayout.setError(context.getString(R.string.fill_the_field));
            return true;
        } else {
            textInputLayout.setError(Constants.EMPTY);
            return false;
        }
    }

    public static boolean isValidEmail(EditText editText, TextInputLayout textInputLayout,
                                       Context context) {
        String email = editText.getText().toString();
        if (!email.isEmpty() && EMAIL_ADDRESS.matcher(email).matches()) {
            textInputLayout.setError(Constants.EMPTY);
            return true;
        } else if (email.isEmpty()) {
            textInputLayout.setError(context.getString(R.string.fill_the_field));
            return false;
        } else {
            textInputLayout.setError(context.getString(R.string.incorrect_email));
            return false;
        }
    }

    public static void createErrorDialog(String message, String alertDialogText, Context context) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton((alertDialogText), null);
        AlertDialog alert = builder.create();
        alert.show();
    }
}
