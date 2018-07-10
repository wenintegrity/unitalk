package com.unitalk.ui.lang;

import com.unitalk.ui.lang.settings_model.Lang;

public interface LangPresenter<V extends LangView> {

    void attachView(V view);

    void detachView();

    void changeLang(Lang lang);
}
