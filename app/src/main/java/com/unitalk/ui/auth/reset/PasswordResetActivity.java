package com.unitalk.ui.auth.reset;

import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;

import com.unitalk.R;
import com.unitalk.ui.auth.BaseAuthActivity;
import com.unitalk.ui.auth.main.AuthMainActivity;
import com.unitalk.utils.FieldsValidator;
import com.unitalk.utils.ViewUpdaterKt;
import com.unitalk.utils.customview.PasswordResetErrorDialog;
import com.unitalk.utils.customview.PasswordResetGoBackDialog;

import butterknife.OnClick;

public class PasswordResetActivity extends BaseAuthActivity implements PasswordResetView {
    protected TextWatcher onButtonStateChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            FieldsValidator.setEmailValidation(etEmail, btnEmailClear);
            setConfirmButtonState();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected int provideLayout() {
        return R.layout.activity_password_reset;
    }

    @Override
    protected void init() {
        super.init();
        etEmail.addTextChangedListener(onButtonStateChangeListener);
        ViewUpdaterKt.goneViews(etPassword);
        tvTitle.setText(R.string.password_reset);
        btnConfirm.setText(R.string.reset);
    }

    @Override
    public void onBackPressed() {
        if (isEditTextEmpty(etEmail)) {
            super.onBackPressed();
        } else {
            createGoBackDialogBox();
        }
    }

    @OnClick(R.id.btnConfirm)
    public void createErrorDialogBox() {
        if (FieldsValidator.isEmailValid(etEmail)) {
            onNextScreen();
        } else {
            final DialogFragment errorDialog = new PasswordResetErrorDialog();
            errorDialog.show(getSupportFragmentManager(), getString(R.string.password_reset_error_title));
        }
    }

    @Override
    protected void onNextScreen() {
        moveToScreenWithoutBack(AuthMainActivity.class);
    }

    @Override
    protected boolean isFieldsLengthAllowable() {
        return !FieldsValidator.isEmailLengthShort(etEmail);
    }

    private void createGoBackDialogBox() {
        final DialogFragment goBackDialog = new PasswordResetGoBackDialog();
        goBackDialog.show(getSupportFragmentManager(), getString(R.string.password_reset_error_title));
    }
}
