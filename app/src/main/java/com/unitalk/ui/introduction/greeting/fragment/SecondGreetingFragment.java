package com.unitalk.ui.introduction.greeting.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.unitalk.R;
import com.unitalk.ui.auth.main.AuthMainActivity;
import com.unitalk.utils.ResourcesManager;

import butterknife.OnClick;

public class SecondGreetingFragment extends BaseGreetingFragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivGreeting.setImageDrawable(ResourcesManager.getDrawable(R.drawable.ic_record_three_samples));
        tvGreetingTitle.setText(R.string.record_three_samples);
        tvGreetingDescription.setText(R.string.record_three_samples_description);
        btnGreetingOk.setText(R.string.ready_to_start);
    }

    @OnClick(R.id.btnGreetingOk)
    public void onNextScreen() {
        final Intent intent = new Intent(getContext(), AuthMainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
