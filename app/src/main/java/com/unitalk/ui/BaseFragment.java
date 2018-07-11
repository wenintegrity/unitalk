package com.unitalk.ui;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unitalk.ui.callback.OnShowMessageCallback;
import com.unitalk.utils.LocaleHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements OnShowMessageCallback {
    private Unbinder unbinder;

    protected abstract int provideLayout();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(provideLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showMessage(final int messageID) {
        Toast.makeText(LocaleHelper.onAttach(getContext()), messageID, Toast.LENGTH_SHORT).show();
    }
}
