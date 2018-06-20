package com.unitalk.utils;

import android.widget.Button;
import android.widget.EditText;

import com.unitalk.R;

public class FieldsValidator {
    private final static int EMAIL_MIN_LENGTH = 4;
    private final static int PASSWORD_MIN_LENGTH = 6;
    private final static int PASSWORD_MAX_LENGTH = 25;
    private final static String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]{2,6}+";

    // FIXME: 4/23/18 repeated code
    public static void setEmailValidation(final EditText etEmail) {
        if (isEmailValid(etEmail)) {
            etEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_tick, 0);
        } else {
            etEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    public static void setEmailValidation(final EditText etEmail, final Button btnClear) {
        etEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        if (isFieldEmpty(etEmail)) {
            ViewUpdaterKt.goneViews(btnClear);
        } else {
            ViewUpdaterKt.showViews(btnClear);
        }
    }

    public static void setNameValidation(final EditText etName) {
        if (isNameValid(etName)) {
            etName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_tick, 0);
        } else {
            etName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    public static void setPasswordValidation(final EditText etPassword) {
        if (isPasswordValid(etPassword)) {
            etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_tick, 0);
        } else {
            etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    public static boolean isNameValid(final EditText etName) {
        final String name = getFieldText(etName);
        return !name.isEmpty();
    }

    public static boolean isEmailValid(final EditText etEmail) {
        final String email = getFieldText(etEmail);
        return email.matches(EMAIL_PATTERN);
    }

    public static boolean isEmailLengthShort(final EditText etEmail) {
        final String email = getFieldText(etEmail);
        return email.length() < EMAIL_MIN_LENGTH;
    }

    public static boolean isPasswordValid(final EditText etPassword) {
        return !isPasswordLengthShort(etPassword) && !isPasswordLengthLong(etPassword);
    }

    public static boolean isPasswordLengthShort(final EditText etEmail) {
        final String password = getFieldText(etEmail);
        return password.length() < PASSWORD_MIN_LENGTH;
    }

    public static boolean isPasswordLengthLong(final EditText etEmail) {
        final String password = getFieldText(etEmail);
        return password.length() > PASSWORD_MAX_LENGTH;
    }

    public static boolean isFieldEmpty(final EditText editText) {
        return getFieldText(editText).isEmpty();
    }

    private static String getFieldText(final EditText editText) {
        return editText.getText().toString().trim();
    }
}
