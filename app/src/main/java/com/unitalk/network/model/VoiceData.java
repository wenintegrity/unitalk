package com.unitalk.network.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VoiceData {
    @SerializedName("data_1")
    @Expose
    private List<Float> firstSampleData;

    @SerializedName("data_2")
    @Expose
    private List<Float> secondSampleData;

    @SerializedName("data_3")
    @Expose
    private List<Float> thirdSampleData;

    public List<Float> getFirstSampleData() {
        return firstSampleData;
    }

    public void setFirstSampleData(@NonNull final List<Float> firstSampleData) {
        this.firstSampleData = firstSampleData;
    }

    public List<Float> getSecondSampleData() {
        return secondSampleData;
    }

    public void setSecondSampleData(@NonNull final List<Float> secondSampleData) {
        this.secondSampleData = secondSampleData;
    }

    public List<Float> getThirdSampleData() {
        return thirdSampleData;
    }

    public void setThirdSampleData(@NonNull final List<Float> thirdSampleData) {
        this.thirdSampleData = thirdSampleData;
    }

    public void setAllData(@NonNull final List<Float> voiceDataList[]) {
        this.firstSampleData = voiceDataList[0];
        this.secondSampleData = voiceDataList[1];
        this.thirdSampleData = voiceDataList[2];
    }

    @Override
    public String toString() {
        return "VoiceData{" +
                "firstSampleData='" + firstSampleData + '\'' +
                ", secondSampleData='" + secondSampleData + '\'' +
                ", thirdSampleData='" + thirdSampleData + '\'' +
                '}';
    }
}
