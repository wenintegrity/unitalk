package com.unitalk.ui.lang.settings_model;

public class LangMessageEvent {

    public static final int UPDATE_LANG = 1;

    private int type;

    public LangMessageEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
