package com.chisw.switchmymind.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 24.01.18.
 */

public class InputData {
    @SerializedName("data_1")
    @Expose
    private List<Byte> data_1;

    @SerializedName("data_2")
    @Expose
    private List<Byte> data_2;

    @SerializedName("data_3")
    @Expose
    private List<Byte>data_3;

    public List<Byte> getData_1() {
        return data_1;
    }

    public void setData_1(List<Byte> data_1) {
        this.data_1 = data_1;
    }

    public List<Byte> getData_2() {
        return data_2;
    }

    public void setData_2(List<Byte> data_2) {
        this.data_2 = data_2;
    }

    public List<Byte> getData_3() {
        return data_3;
    }

    public void setData_3(List<Byte> data_3) {
        this.data_3 = data_3;
    }

    @Override
    public String toString() {
        return "InputData{" +
                "data_1='" + data_1 + '\'' +
                ", data_2='" + data_2 + '\'' +
                ", data_3='" + data_3 + '\'' +
                '}';
    }
}
