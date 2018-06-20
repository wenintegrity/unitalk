package com.unitalk.ui.introduction.greeting.factory;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.unitalk.ui.introduction.greeting.fragment.BaseGreetingFragment;
import com.unitalk.ui.introduction.greeting.fragment.SecondGreetingFragment;

public class GreetingFragmentFactory {
    public enum FragmentType {
        FIRST, SECOND
    }

    @NonNull
    public static Fragment getFragment(final FragmentType type) {
        switch (type) {
            case FIRST:
                return new BaseGreetingFragment();
            case SECOND:
                return new SecondGreetingFragment();
        }
        return null;
    }
}
