package com.unitalk.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HarmonyResultsData {
    @SerializedName("result_1")
    @Expose
    private double firstResult;

    @SerializedName("result_2")
    @Expose
    private double secondResult;

    public double getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(final double firstResult) {
        this.firstResult = firstResult;
    }

    public double getSecondResult() {
        return secondResult;
    }

    public void setSecondResult(final double secondResult) {
        this.secondResult = secondResult;
    }
}
