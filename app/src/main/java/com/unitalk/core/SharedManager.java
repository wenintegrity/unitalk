package com.unitalk.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.unitalk.enums.CardsState;

public class SharedManager {
    private static final float VOICE_LEVEL_DEFAULT_VALUE = -35f;
    private static final String DEFAULT_STRING = "";
    private static final int DEFAULT_INT = 1;
    private static final String APP_PREFS = "SharedPrefs";
    private static final String PREFS_UNIQUE_ID = "uniqueID";
    private static final String PREFS_CARDS_STATE = "cardsState";
    private static final String PREFS_FIRST_LOGIN = "firstLogin";
    private static final String PREFS_VOICE_LEVEL = "voiceLevel";
    private static final String PREFS_RESULTS = "results";
    private static final String PREFS_SESSION_ID = "sessionID";
    private static final String PREFS_CALCULATION_ID = "calculationID";
    private static final String PREFS_DIALOG_CLOSED = "dialogClosed";
    private static final String PREFS_IS_INTRO_VIDEO = "isIntroVideo";
    private static final String PREFS_IS_VIDEO_SCREEN = "isVideoScreen";
    private static final String PREFS_CURRENT_VIDEO_URL = "currentVideoUrl";

    private SharedPreferences sharedPreferences;

    SharedManager(@NonNull final Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }

    public String getUniqueID() {
        return sharedPreferences.getString(PREFS_UNIQUE_ID, null);
    }

    public void setUniqueID(@NonNull final String uniqueID) {
        sharedPreferences.edit().putString(PREFS_UNIQUE_ID, uniqueID).apply();
    }

    public String getCardsState() {
        return sharedPreferences.getString(PREFS_CARDS_STATE, CardsState.FOUR.name());
    }

    public void setCardsState(final String cardsState) {
        sharedPreferences.edit().putString(PREFS_CARDS_STATE, cardsState).apply();
    }

    public boolean getFirstLoginState() {
        return sharedPreferences.getBoolean(PREFS_FIRST_LOGIN, true);
    }

    public void setFirstLoginState(final boolean firstLoginState) {
        sharedPreferences.edit().putBoolean(PREFS_FIRST_LOGIN, firstLoginState).apply();
    }

    public float getVoiceLevel() {
        return sharedPreferences.getFloat(PREFS_VOICE_LEVEL, VOICE_LEVEL_DEFAULT_VALUE);
    }

    public void setVoiceLevel(final float voiceLevel) {
        sharedPreferences.edit().putFloat(PREFS_VOICE_LEVEL, voiceLevel).apply();
    }

    public String getResults() {
        return sharedPreferences.getString(PREFS_RESULTS, DEFAULT_STRING);
    }

    public void setResults(final String results) {
        sharedPreferences.edit().putString(PREFS_RESULTS, results).apply();
    }

    public String getSessionID() {
        return sharedPreferences.getString(PREFS_SESSION_ID, DEFAULT_STRING);
    }

    public void setSessionID(final String sessionID) {
        sharedPreferences.edit().putString(PREFS_SESSION_ID, sessionID).apply();
    }

    public String getCalculationID() {
        return sharedPreferences.getString(PREFS_CALCULATION_ID, DEFAULT_STRING);
    }

    public void setCalculationID(final String calculationID) {
        sharedPreferences.edit().putString(PREFS_CALCULATION_ID, calculationID).apply();
    }

    public boolean getDialogClosed() {
        return sharedPreferences.getBoolean(PREFS_DIALOG_CLOSED, false);
    }

    public void setDialogClosed(final boolean dialogClosed) {
        sharedPreferences.edit().putBoolean(PREFS_DIALOG_CLOSED, dialogClosed).apply();
    }

    public boolean getIsIntroVideo() {
        return sharedPreferences.getBoolean(PREFS_IS_INTRO_VIDEO, true);
    }

    public void setIsIntroVideo(final boolean isIntroVideo) {
        sharedPreferences.edit().putBoolean(PREFS_IS_INTRO_VIDEO, isIntroVideo).apply();
    }

    public boolean getIsVideoScreen() {
        return sharedPreferences.getBoolean(PREFS_IS_VIDEO_SCREEN, true);
    }

    public void setIsVideoScreen(final boolean isVideoScreen) {
        sharedPreferences.edit().putBoolean(PREFS_IS_VIDEO_SCREEN, isVideoScreen).apply();
    }

    public String getCurrentVideoUrl() {
        return sharedPreferences.getString(PREFS_CURRENT_VIDEO_URL, DEFAULT_STRING);
    }

    public void setCurrentVideoUrl(final String calculationID) {
        sharedPreferences.edit().putString(PREFS_CURRENT_VIDEO_URL, calculationID).apply();
    }
}
