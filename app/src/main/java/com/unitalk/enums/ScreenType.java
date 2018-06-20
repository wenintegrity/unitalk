package com.unitalk.enums;

public enum ScreenType {
    RECORDING(0), DAILY(1), VIDEOS(2), SETTINGS(3);

    int value;

    ScreenType(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
