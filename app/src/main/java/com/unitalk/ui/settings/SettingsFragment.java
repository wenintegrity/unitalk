package com.unitalk.ui.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.unitalk.R;
import com.unitalk.ui.BaseFragment;
import com.unitalk.ui.settings.SettingsPresenter;
import com.unitalk.ui.settings.SettingsPresenterImpl;
import com.unitalk.ui.settings.SettingsView;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsFragment extends BaseFragment implements SettingsView {
    private SettingsPresenter<SettingsView> presenter;

    @BindView(R.id.etVoiceLevel)
    EditText etVoiceLevel;

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
        Toast.makeText(getContext(), R.string.voice_level_changed, Toast.LENGTH_SHORT).show();
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
}
