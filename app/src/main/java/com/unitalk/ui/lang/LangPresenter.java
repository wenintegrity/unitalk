package com.unitalk.ui.lang;

public interface LangPresenter<V extends LangView> {

    void attachView(V view);

    void detachView();

    void changeLang(Lang lang);
}
