package com.unitalk.ui.introduction.greeting;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.unitalk.R;
import com.unitalk.ui.BaseActivity;
import com.unitalk.ui.introduction.greeting.OnViewPagerNavigation;
import com.unitalk.ui.introduction.greeting.adapter.GreetingFragmentAdapter;

public class GreetingActivity extends BaseActivity implements OnViewPagerNavigation {
    private ViewPager viewPager;

    @Override
    protected int provideLayout() {
        return R.layout.activity_greeting;
    }

    @Override
    protected View getRootView() {
        return findViewById(R.id.home);
    }

    @Override
    protected void init() {
        super.init();
        initPager();
    }

    private void initPager() {
        final GreetingFragmentAdapter adapter = new GreetingFragmentAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager_greeting_activity);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        final TabLayout tabLayout = findViewById(R.id.activity_greeting_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onNextScreen(final int pageNumber) {
        viewPager.setCurrentItem(1);
    }
}