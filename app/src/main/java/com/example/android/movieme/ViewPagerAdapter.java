package com.example.android.movieme;

import android.content.Context;
import android.support.v4.app.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marina on 28.09.2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter{

   private final List<Fragment> mFragmentList = new ArrayList<>();
   private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
}
