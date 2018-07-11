package com.unitalk.ui.videos;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.unitalk.BuildConfig;
import com.unitalk.R;
import com.unitalk.core.App;
import com.unitalk.core.WebAppInterface;
import com.unitalk.enums.CardsState;
import com.unitalk.ui.BaseFragment;
import com.unitalk.ui.callback.OnActionCallback;
import com.unitalk.ui.callback.OnMainActivityCallback;
import com.unitalk.ui.lang.FragmentUpdateEvent;
import com.unitalk.utils.LocaleHelper;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class VideosFragment extends BaseFragment implements VideosView, MediaPlayer.OnCompletionListener, OnActionCallback, FragmentUpdateEvent {
    private static final String JS_INJECTION_URL = "javascript:document.getElementsByTagName('video')[0].onended=function(){android.onPageEnded()}";
    private static final String JS_INTERFACE_NAME = "android";
    private static final int JS_INJECTION_DELAY = 3000;
    private boolean isIntroVideo;
    private VideosPresenter<VideosView> presenter;
    private OnMainActivityCallback onMainActivityCallback;
    private Disposable disposable;

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.tvSkipVideo)
    TextView tvSkipVideo;

    public static VideosFragment newInstance() {
        return new VideosFragment();
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_videos;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainActivityCallback) {
            onMainActivityCallback = (OnMainActivityCallback) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new VideosPresenterImpl(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void onCompletion(final MediaPlayer mp) {
        if (isIntroVideo || App.getInstance().getSharedManager().getIsVideoScreen()) {
            onMainActivityCallback.startVideoFragment();
        } else {
            presenter.moveToCardsScreen();
        }
    }

    // FIXME: 5/17/18 remove this
    @OnClick(R.id.tvSkipVideo)
    public void onVideoFinished() {
        onCompletion(null);
    }

    @Override
    public void moveToNextScreen() {
        onCompletion(null);
    }

    // FIXME: 5/22/18 Divide this enormous method
    public void init() {
        isIntroVideo = App.getInstance().getSharedManager().getIsIntroVideo();
        final WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);

        if (App.getInstance().getSharedManager().getIsVideoScreen()) {
            webView.loadUrl(App.getInstance().getSharedManager().getCurrentVideoUrl());
        } else {
            webView.addJavascriptInterface(new WebAppInterface(this), JS_INTERFACE_NAME);
            final WebViewClient webViewClient = new WebViewClient() {
                @Override
                public void onPageFinished(final WebView view, final String url) {
                    disposable = Observable.intervalRange(0, 1, 0, JS_INJECTION_DELAY, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnNext(aLong -> view.loadUrl(JS_INJECTION_URL))
                            .subscribe();
                }
            };
            webView.setWebViewClient(webViewClient);

            final String cardState = App.getInstance().getSharedManager().getCardsState();
            if (cardState.equals(CardsState.FOUR.name())) {
                if (isIntroVideo) {
                    App.getInstance().getSharedManager().setCurrentVideoUrl(BuildConfig.VIDEO_1);
                    App.getInstance().getSharedManager().setIsIntroVideo(false);
                } else {
                    App.getInstance().getSharedManager().setCurrentVideoUrl(BuildConfig.VIDEO_2);
                }
            } else {
                App.getInstance().getSharedManager().setCurrentVideoUrl(BuildConfig.VIDEO_3);
            }

            webView.loadUrl(App.getInstance().getSharedManager().getCurrentVideoUrl());
        }
    }

    @Override
    public void goToCardsFragment(final boolean isSixCardsFragment) {
        if (onMainActivityCallback != null) {
            onMainActivityCallback.startHarmonyCheckingCardFragment(presenter.createSixMoodItemsList(isSixCardsFragment));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    @Override
    public void updateView() {
        Resources resources = LocaleHelper.getResources(getContext());
        tvSkipVideo.setText(resources.getString(R.string.skip));
    }
}
