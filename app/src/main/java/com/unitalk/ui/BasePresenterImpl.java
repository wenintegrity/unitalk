package com.unitalk.ui;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenterImpl implements BasePresenter {
    protected CompositeDisposable compositeDisposable;

    public void onStart() {
        compositeDisposable = new CompositeDisposable();
    }

    public void onStop() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
