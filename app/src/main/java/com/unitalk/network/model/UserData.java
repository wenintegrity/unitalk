package com.unitalk.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("data")
    @Expose
    private VoiceData voiceData;
    @SerializedName("location")
    @Expose
    private CurrentLocationData location;
    @SerializedName("time")
    @Expose
    private String time;

    public UserData() {
        voiceData = new VoiceData();
        location = new CurrentLocationData();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public VoiceData getVoiceData() {
        return voiceData;
    }

    public void setVoiceData(final VoiceData voiceData) {
        this.voiceData.setFirstSampleData(voiceData.getFirstSampleData());
        this.voiceData.setSecondSampleData(voiceData.getSecondSampleData());
        this.voiceData.setThirdSampleData(voiceData.getThirdSampleData());
    }

    public CurrentLocationData getLocation() {
        return location;
    }

    public void setLocation(final CurrentLocationData currentLocationData) {
        location.setLatitude(currentLocationData.getLatitude());
        location.setLongitude(currentLocationData.getLongitude());
    }

    public String getTime() {
        return time;
    }

    public void setTime(final String time) {
        this.time = time;
    }
}
