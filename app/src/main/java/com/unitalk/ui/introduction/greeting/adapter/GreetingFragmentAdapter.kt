package com.unitalk.ui.introduction.greeting.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.unitalk.ui.introduction.greeting.factory.GreetingFragmentFactory

class GreetingFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return GreetingFragmentFactory.getFragment(GreetingFragmentFactory.FragmentType.values()[position])
    }

    override fun getCount(): Int {
        return GreetingFragmentFactory.FragmentType.values().size
    }
}
