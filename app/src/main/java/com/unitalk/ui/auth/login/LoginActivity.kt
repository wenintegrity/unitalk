package com.unitalk.ui.auth.login

import com.unitalk.R
import com.unitalk.ui.auth.BaseAuthActivity
import com.unitalk.ui.auth.reset.PasswordResetActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity: BaseAuthActivity(), LoginView {
    override fun provideLayout(): Int {
        return R.layout.activity_login
    }

    override fun init() {
        super.init()
        tvTitle.setText(R.string.log_in)
        btnConfirm.setText(R.string.log_in)
        tvForgotPassword.setOnClickListener { moveToPasswordResetScreen() }
    }

    private fun moveToPasswordResetScreen() {
        moveToScreen(PasswordResetActivity::class.java)
    }
}