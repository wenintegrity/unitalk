package com.unitalk.network.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FirstSamplingResultsData {
    @SerializedName("session_id")
    @Expose
    private String sessionID;

    @SerializedName("calc_id")
    @Expose
    private String calculationID;

    @SerializedName("result")
    @Expose
    private HarmonyResultsData harmonyResultsData;

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(@NonNull final String sessionID) {
        this.sessionID = sessionID;
    }

    public String getCalculationID() {
        return calculationID;
    }

    public void setCalculationID(@NonNull final String calculationID) {
        this.calculationID = calculationID;
    }

    public HarmonyResultsData getHarmonyResultsData() {
        return harmonyResultsData;
    }

    public void setHarmonyResultsData(@NonNull final HarmonyResultsData harmonyResultsData) {
        this.harmonyResultsData = harmonyResultsData;
    }
}

