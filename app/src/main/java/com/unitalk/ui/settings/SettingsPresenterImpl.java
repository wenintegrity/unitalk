package com.unitalk.ui.settings;

import android.support.annotation.NonNull;

import com.unitalk.R;
import com.unitalk.core.App;
import com.unitalk.ui.BasePresenterImpl;
import com.unitalk.ui.settings.SettingsView;

public class SettingsPresenterImpl extends BasePresenterImpl implements SettingsPresenter<SettingsView> {
    private static final float MAX_VOICE_LEVEL = 0;
    private static final float MIN_VOICE_LEVEL = -45;
    private SettingsView view;

    public SettingsPresenterImpl(final SettingsView view) {
        this.view = view;
    }

    @Override
    public void setVoiceLevel(@NonNull final String voiceLevel) {
        try {
            final float floatVoiceLevel = Float.parseFloat(voiceLevel);
            if (isVoiceLevelCorrect(floatVoiceLevel)) {
                App.getInstance().getSharedManager().setVoiceLevel(floatVoiceLevel);
                view.onVoiceLevelChanged();
            } else {
                view.showMessage(R.string.enter_correct_value);
            }
        } catch (final NumberFormatException ex) {
            view.showMessage(R.string.enter_correct_value);
        }
    }

    private boolean isVoiceLevelCorrect(final float voiceLevel) {
        return voiceLevel >= MIN_VOICE_LEVEL && voiceLevel <= MAX_VOICE_LEVEL;
    }
}
