package com.unitalk.ui.result;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.unitalk.R;
import com.unitalk.ui.BaseFragment;
import com.unitalk.ui.callback.OnScreenNavigationCallback;
import com.unitalk.ui.home.HomeActivity;
import com.unitalk.ui.lang.FragmentUpdateEvent;
import com.unitalk.utils.LocaleHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class ResultFragment extends BaseFragment implements ResultView, FragmentUpdateEvent {
    @BindView(R.id.tv_percents_result)
    TextView tvResultHint;
    @BindView(R.id.tvOK)
    TextView tvOK;
    @BindView(R.id.tvResultLabel)
    TextView tvResultLabel;
    @BindView(R.id.tvHarmonyChkResult)
    TextView tvHarmonyChkResult;

    private ResultPresenter presenter;
    private OnScreenNavigationCallback onScreenNavigationCallback;

    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_result;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof OnScreenNavigationCallback) {
            onScreenNavigationCallback = (OnScreenNavigationCallback) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ResultPresenterImpl(this);
        presenter.sendCardsClickedData();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.refreshResults();
    }

    @Override
    public void onResultsUpdated(final String s) {
        tvResultHint.setText(s);
    }

    @OnClick(R.id.tvOK)
    public void onNextScreen() {
        if (onScreenNavigationCallback != null) {
            onScreenNavigationCallback.moveToScreenWithoutBack(HomeActivity.class);
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
        tvOK.setText(resources.getString(R.string.ok));
        tvResultLabel.setText(resources.getString(R.string.harmony_result));
        tvHarmonyChkResult.setText(resources.getString(R.string.harmony_checking));
    }
}
