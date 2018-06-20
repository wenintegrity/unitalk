package com.unitalk.ui.auth.signup

import com.unitalk.R
import com.unitalk.ui.auth.BaseAuthActivity
import com.unitalk.utils.FieldsValidator
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : BaseAuthActivity(), SignupView {
    override fun provideLayout(): Int {
        return R.layout.activity_signup
    }

    override fun init() {
        super.init()
        etName.addTextChangedListener(onButtonStateChangeListener)
        etName.requestFocus()
        tvTitle.setText(R.string.email_registration)
        btnConfirm.setText(R.string.get_started)
        btnConfirm.setOnClickListener {
            if (isSignupDataValid()) {
                onNextScreen()
            }
        }
    }

    override fun setFieldsValidation() {
        super.setFieldsValidation()
        FieldsValidator.setNameValidation(etName)
    }

    override fun isFieldsLengthAllowable(): Boolean {
        return super.isFieldsLengthAllowable() && !FieldsValidator.isFieldEmpty(etName)
    }

    private fun isSignupDataValid(): Boolean {
        return isLoginDataValid && FieldsValidator.isNameValid(etName)
    }
}