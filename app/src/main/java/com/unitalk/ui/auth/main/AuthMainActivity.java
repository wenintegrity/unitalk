package com.unitalk.ui.auth.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.unitalk.BuildConfig;
import com.unitalk.R;
import com.unitalk.ui.BaseActivity;
import com.unitalk.ui.auth.login.LoginActivity;
import com.unitalk.ui.auth.signup.SignupActivity;
import com.unitalk.ui.callback.OnShowMessageCallback;
import com.unitalk.ui.home.HomeActivity;
import com.unitalk.ui.introduction.authinfo.VoiceInfoActivity;
import com.unitalk.ui.lang.LangActivity;
import com.unitalk.utils.LocaleHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthMainActivity extends BaseActivity implements AuthMainView, OnShowMessageCallback {
    private static final String EMAIL_PERMISSION = "email";
    private static final int RC_SIGN_IN = 7;
    private AuthMainPresenter<AuthMainView> presenter;
    private GoogleApiClient googleApiClient;

    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.tvLoginInsteadAuth)
    TextView tvLogInInstead;
    @BindView(R.id.btnGoogle)
    RelativeLayout btnGoogleAuth;
    @BindView(R.id.llSignup)
    LinearLayout llSignup;
    @BindView(R.id.tvTermsAndConditions)
    TextView tvTermsAndConditions;
    @BindView(R.id.tvLang)
    TextView tvLang;
    @BindView(R.id.sign_up_title)
    TextView signUpTitle;
    @BindView(R.id.sign_up_title_detail)
    TextView signUpTitleDetail;


    @Override
    protected int provideLayout() {
        return R.layout.activity_auth;
    }

    @Override
    protected View getRootView() {
        return findViewById(R.id.home);
    }

    @Override
    protected void init() {
        super.init();
        loginButton.setReadPermissions(EMAIL_PERMISSION);
        presenter = new AuthMainPresenterImpl(this, this);
        presenter.createFacebookAuth();
        initGoogleAuth();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.checkCurrentGoogleAcc(googleApiClient);
    }

    @OnClick({R.id.btnGoogle, R.id.llSignup, R.id.tvLoginInsteadAuth, R.id.tvLang})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoogle:
                final Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
            case R.id.llSignup:
                moveToScreen(SignupActivity.class);
                break;
            case R.id.tvLoginInsteadAuth:
                moveToScreen(LoginActivity.class);
                break;
            case R.id.tvLang:
                moveToScreen(LangActivity.class);
                break;
        }
    }

    @Override
    public void onAuthSuccessful() {
        Toast.makeText(this, R.string.welcome, Toast.LENGTH_SHORT).show();
        if (getFirstLogin()) {
            moveToScreenWithoutBack(VoiceInfoActivity.class);
        } else {
            moveToScreenWithoutBack(HomeActivity.class);
        }
    }

    @OnClick(R.id.tvTermsAndConditions)
    public void openPrivatePolicyRules() {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.PRIVATE_POLICY_URL));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        presenter.goToFacebookAuthResult(requestCode, resultCode, data);
        presenter.handleGoogleResult(requestCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initGoogleAuth() {
        final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, connectionResult -> {
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateViews();
    }

    private void updateViews() {
        Context context = LocaleHelper.onAttach(this);
        Resources resources = context.getResources();

        tvLogInInstead.setText(resources.getString(R.string.log_in_instead_auth));
        tvTermsAndConditions.setText(resources.getString(R.string.terms_and_conditions_auth));
        tvLang.setText(resources.getString(R.string.change_language));
        signUpTitle.setText(resources.getString(R.string.sign_up_title_auth));
        signUpTitleDetail.setText(resources.getString(R.string.sign_up_title_descr_auth));
    }
}