package com.unitalk.ui.introduction.greeting.fragment;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.unitalk.R;
import com.unitalk.ui.BaseFragment;
import com.unitalk.ui.introduction.greeting.OnViewPagerNavigation;
import com.unitalk.ui.introduction.greeting.GreetingActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class BaseGreetingFragment extends BaseFragment {
    private OnViewPagerNavigation onViewPagerNavigation;

    @BindView(R.id.ivGreeting)
    protected ImageView ivGreeting;
    @BindView(R.id.tvGreetingTitle)
    protected TextView tvGreetingTitle;
    @BindView(R.id.tvGreetingDescription)
    protected TextView tvGreetingDescription;
    @BindView(R.id.btnGreetingOk)
    protected Button btnGreetingOk;

    @Override
    protected int provideLayout() {
        return R.layout.fragment_base_greeting;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        onViewPagerNavigation = (GreetingActivity) context;
    }

    @OnClick(R.id.btnGreetingOk)
    public void onNextScreen() {
        onViewPagerNavigation.onNextScreen(1);
    }
}
