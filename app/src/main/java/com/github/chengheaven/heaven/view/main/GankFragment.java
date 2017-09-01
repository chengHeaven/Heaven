package com.github.chengheaven.heaven.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.application.App;
import com.github.chengheaven.heaven.di.component.DaggerGankComponent;
import com.github.chengheaven.heaven.di.module.GankFragmentModule;
import com.github.chengheaven.heaven.helper.BaseFragment;
import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.presenter.gank.AndroidPresenter;
import com.github.chengheaven.heaven.presenter.gank.CustomizationPresenter;
import com.github.chengheaven.heaven.presenter.gank.EveryPresenter;
import com.github.chengheaven.heaven.presenter.gank.WelfarePresenter;
import com.github.chengheaven.heaven.presenter.mainFragment.GankContract;
import com.github.chengheaven.heaven.view.main.gank.AndroidFragment;
import com.github.chengheaven.heaven.view.main.gank.CustomizationFragment;
import com.github.chengheaven.heaven.view.main.gank.EveryFragment;
import com.github.chengheaven.heaven.view.main.gank.WelfareFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

public class GankFragment extends BaseFragment implements GankContract.View {

    @BindView(R.id.viewpager_gank)
    ViewPager mViewpager;
    @BindView(R.id.tab_gank)
    TabLayout mTab;

    @Inject
    AndroidPresenter mAndroidPresenter;
    @Inject
    CustomizationPresenter mCustomPresenter;
    @Inject
    EveryPresenter mEveryPresenter;
    @Inject
    WelfarePresenter mWelfarePresenter;

    private GankContract.Presenter mPresenter;

    public static GankFragment newInstance() {
        return new GankFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_gank, container, false);
        ButterKnife.bind(this, view);

        List<Fragment> fragments = new ArrayList<>();
        EveryFragment everyFragment = EveryFragment.newInstance();
        WelfareFragment welfareFragment = WelfareFragment.newInstance();
        CustomizationFragment customizationFragment = CustomizationFragment.newInstance();
        AndroidFragment androidFragment = AndroidFragment.newInstance();

        fragments.add(everyFragment);
        fragments.add(welfareFragment);
        fragments.add(customizationFragment);
        fragments.add(androidFragment);

        List<String> titles = new ArrayList<>();
        titles.add("每日推荐");
        titles.add("福利");
        titles.add("干货定制");
        titles.add("大安卓");

        TabPagerAdapter adapter = new TabPagerAdapter(getChildFragmentManager(), fragments, titles);
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(2);
        mTab.setupWithViewPager(mViewpager);
        mTab.setTabMode(TabLayout.MODE_FIXED);

        DaggerGankComponent.builder()
                .dataRepositoryComponent(((App) getActivity().getApplication()).getDataRepositoryComponent())
                .gankFragmentModule(new GankFragmentModule(
                        everyFragment,
                        welfareFragment,
                        customizationFragment,
                        androidFragment
                ))
                .build()
                .inject(this);

        mPresenter.start();

        return view;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (GankContract.Presenter) presenter;
    }

    @Override
    public void setCurrentItem(int position) {
        mViewpager.setCurrentItem(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    private class TabPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;

        TabPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}
