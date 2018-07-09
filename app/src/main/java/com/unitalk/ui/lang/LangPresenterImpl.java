package com.unitalk.ui.lang;

import android.content.Context;

import com.unitalk.utils.LocaleHelper;

public class LangPresenterImpl implements LangPresenter {

    private LangView view;
    private Context context;

    @Override
    public void attachView(LangView view) {
        this.view = view;
        this.context = ((LangActivity) view).getBaseContext();
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void changeLang(Lang lang) {
        LocaleHelper.setLocale(context, lang.getLocale());
    }
}
