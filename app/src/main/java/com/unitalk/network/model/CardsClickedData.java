package com.unitalk.network.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardsClickedData {
    @SerializedName("pictures")
    @Expose
    private List<String> cardsClickedList;

    public List<String> getCardsClickedList() {
        return cardsClickedList;
    }

    public void setCardsClickedList(@NonNull final List<String> cardsClickedList) {
        this.cardsClickedList = cardsClickedList;
    }
}
