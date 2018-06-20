package com.unitalk.utils;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class StringArrayDataLocalStorage {
    private List<String> arrayData;
    private static StringArrayDataLocalStorage instance;

    private StringArrayDataLocalStorage() {
        arrayData = new ArrayList<>();
    }

    public static StringArrayDataLocalStorage getInstance() {
        if (instance == null) {
            instance = new StringArrayDataLocalStorage();
        }
        return instance;
    }

    public void add(@NonNull final String data) {
        arrayData.add(data);
    }

    public List<String> getArrayData() {
        return arrayData;
    }

    public void setArrayData(@NonNull final List<String> arrayData) {
        this.arrayData = arrayData;
    }

    public void clearArrayData() {
        arrayData.clear();
    }

    public boolean isArrayDataEmpty() {
        return arrayData.isEmpty();
    }
}
