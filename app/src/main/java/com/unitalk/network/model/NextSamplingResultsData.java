package com.unitalk.network.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NextSamplingResultsData {
    @SerializedName("calc_id")
    @Expose
    private String calculationID;

    @SerializedName("result")
    @Expose
    private HarmonyResultsData harmonyResultsData;

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

