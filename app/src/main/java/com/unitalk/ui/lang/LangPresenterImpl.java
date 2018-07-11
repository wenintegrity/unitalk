package com.unitalk.ui.lang;

import android.content.Context;

import com.unitalk.ui.lang.settings_model.Lang;
import com.unitalk.ui.lang.settings_model.LangMessageEvent;
import com.unitalk.utils.LocaleHelper;

import org.greenrobot.eventbus.EventBus;

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
        EventBus.getDefault().post(new LangMessageEvent(LangMessageEvent.UPDATE_LANG));
        view.updateList();
    }
}
