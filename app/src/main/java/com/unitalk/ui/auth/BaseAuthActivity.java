package com.unitalk.ui.auth;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.unitalk.R;
import com.unitalk.ui.BaseActivity;
import com.unitalk.ui.home.HomeActivity;
import com.unitalk.ui.introduction.authinfo.VoiceInfoActivity;
import com.unitalk.utils.FieldsValidator;
import com.unitalk.utils.ResourcesManager;
import com.unitalk.utils.ViewUpdaterKt;

import butterknife.BindView;
import butterknife.OnClick;

public abstract class BaseAuthActivity extends BaseActivity {
    @BindView(R.id.ivBack)
    protected ImageView ivBack;
    @BindView(R.id.tvTitle)
    protected TextView tvTitle;
    @BindView(R.id.etEmail)
    protected EditText etEmail;
    @BindView(R.id.btnEmailClear)
    protected Button btnEmailClear;
    @BindView(R.id.tvEnterValidEmail)
    protected TextView tvEnterValidEmail;
    @BindView(R.id.etPassword)
    protected EditText etPassword;
    @BindView(R.id.tvTooShortPassword)
    protected TextView tvTooShortPassword;
    @BindView(R.id.btnConfirm)
    protected Button btnConfirm;

    protected TextWatcher onButtonStateChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (etEmail.isFocused()) {
                ViewUpdaterKt.hideViews(tvEnterValidEmail);
            }
            if (etPassword.isFocused()) {
                ViewUpdaterKt.hideViews(tvTooShortPassword);
            }
            setFieldsValidation();
            setConfirmButtonState();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected View getRootView() {
        return findViewById(R.id.rlAuth);
    }

    @Override
    protected void init() {
        super.init();
        etEmail.addTextChangedListener(onButtonStateChangeListener);
        etPassword.addTextChangedListener(onButtonStateChangeListener);
        etEmail.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    protected boolean isFieldsLengthAllowable() {
        return !FieldsValidator.isEmailLengthShort(etEmail) &&
                !FieldsValidator.isPasswordLengthShort(etPassword);
    }

    protected void setConfirmButtonState() {
        if (isFieldsLengthAllowable()) {
            btnConfirm.setBackgroundColor(ResourcesManager.getColor(R.color.colorAccentLight));
            btnConfirm.setEnabled(true);
        } else {
            btnConfirm.setBackgroundColor(ResourcesManager.getColor(R.color.colorGreyButton));
            btnConfirm.setEnabled(false);
        }
    }

    protected void setFieldsValidation() {
        FieldsValidator.setEmailValidation(etEmail);
        FieldsValidator.setPasswordValidation(etPassword);
    }

    protected boolean isLoginDataValid() {
        return FieldsValidator.isEmailValid(etEmail) && FieldsValidator.isPasswordValid(etPassword);
    }

    protected boolean isEditTextEmpty(@NonNull final EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    @OnClick({R.id.ivBack, R.id.btnConfirm, R.id.btnEmailClear})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnConfirm:
                ViewUpdaterKt.hideViews(tvEnterValidEmail, tvTooShortPassword);
                if (!FieldsValidator.isEmailValid(etEmail)) {
                    ViewUpdaterKt.showViews(tvEnterValidEmail);
                }
                if (!FieldsValidator.isPasswordValid(etPassword)) {
                    ViewUpdaterKt.showViews(tvTooShortPassword);
                }
                if (isLoginDataValid()) {
                    onNextScreen();
                }
                break;
            case R.id.btnEmailClear:
                clearEmailField();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    protected void onNextScreen() {
        if (getFirstLogin()) {
            moveToScreenWithoutBack(VoiceInfoActivity.class);
        } else {
            moveToScreenWithoutBack(HomeActivity.class);
        }
    }

    private void clearEmailField() {
        etEmail.setText("");
    }
}
