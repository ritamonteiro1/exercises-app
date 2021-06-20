package com.example.leal.utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.leal.R;
import com.example.leal.constants.Constants;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.util.Patterns.EMAIL_ADDRESS;

public class Utils {
    public static boolean isEmptyField(TextInputLayout textInputLayout, EditText editText,
                                       Context context) {
        String value = editText.getText().toString();
        if (value.isEmpty()) {
            textInputLayout.setError(context.getString(R.string.login_error_fill_the_field));
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
            textInputLayout.setError(context.getString(R.string.login_error_fill_the_field));
            return false;
        } else {
            textInputLayout.setError(context.getString(R.string.login_error_incorrect_email));
            return false;
        }
    }

    public static void createErrorDialog(String message, String positiveButtonText, Context context) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton((positiveButtonText), null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void createAlertDialogWithQuestion(String message, Context context,
                                                     DialogInterface.OnClickListener onPositiveClickListener) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(
                        (context.getString(R.string.positive_message_question_alert_dialog)),
                        onPositiveClickListener
                )
                .setNegativeButton(context.getString(R.string.negative_message_question_alert_dialog), null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static ProgressDialog showProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static String convertTimestampToString(Timestamp timestamp) {
        Date date = timestamp.toDate();
        DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Constants.LOCALE_BR);
        return dateFormat.format(date);
    }
}