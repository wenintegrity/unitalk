package com.unitalk.ui.lang;

public enum Lang {

    ENGLISH("English", "English", "en"), RUSSIAN("Русский", "Russian", "ru");

    private String name;
    private String engName;
    private String locale;

    private Lang(String name, String engName, String locale) {
        this.name = name;
        this.engName = engName;
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public String getLocale() {
        return locale;
    }

    public String getEngName() {
        return engName;
    }
}
