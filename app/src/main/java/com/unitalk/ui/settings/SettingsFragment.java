package com.unitalk.ui.settings;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unitalk.R;
import com.unitalk.core.App;
import com.unitalk.ui.BaseFragment;
import com.unitalk.ui.auth.main.AuthMainActivity;
import com.unitalk.ui.lang.FragmentUpdateEvent;
import com.unitalk.ui.lang.LangActivity;
import com.unitalk.utils.FacebookAuthManager;
import com.unitalk.utils.LocaleHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsFragment extends BaseFragment implements SettingsView, FragmentUpdateEvent {
    private SettingsPresenter<SettingsView> presenter;

    @BindView(R.id.etVoiceLevel)
    EditText etVoiceLevel;
    @BindView(R.id.btn_lang)
    FrameLayout btnLang;
    @BindView(R.id.btn_logOut)
    FrameLayout btnLogOut;
    @BindView(R.id.voice_level_label)
    TextView voiceLevelLabel;
    @BindView(R.id.btnVoiceLevelSave)
    ImageView btnVoiceLevelSave;
    @BindView(R.id.btn_lang_label)
    TextView btnLangLabel;
    @BindView(R.id.btn_logOut_label)
    TextView btnLogOutLabel;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_settings;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new SettingsPresenterImpl(this);
    }

    @Override
    public void onVoiceLevelChanged() {
        etVoiceLevel.setText("");
        etVoiceLevel.clearFocus();
        Toast.makeText(getContext(), LocaleHelper.getResources(getContext()).getString(R.string.voice_level_changed), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnVoiceLevelSave)
    public void saveVoiceLevel() {
        final String voiceLevel = etVoiceLevel.getText().toString();
        if (voiceLevel.isEmpty()) {
            showMessage(R.string.enter_correct_value);
        } else {
            presenter.setVoiceLevel(voiceLevel);
        }
    }

    @OnClick({R.id.btn_lang, R.id.btn_logOut})
    public void click(View v) {

        switch (v.getId()) {
            case R.id.btn_lang:
                Intent intentLang = new Intent(getContext(), LangActivity.class);
                startActivity(intentLang);
                break;
            case R.id.btn_logOut:
                App.getInstance().getSharedManager().setUniqueID("");
                Intent intentLogOut = new Intent(getContext(), AuthMainActivity.class);
                intentLogOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentLogOut);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    public void updateView() {
        Resources resources = LocaleHelper.getResources(getContext());
        voiceLevelLabel.setText(resources.getString(R.string.voice_level_db));
        btnLangLabel.setText(resources.getString(R.string.change_language));
        btnLogOutLabel.setText(resources.getString(R.string.log_out));
    }
}
