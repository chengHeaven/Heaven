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
import com.github.chengheaven.heaven.di.component.DaggerBookComponent;
import com.github.chengheaven.heaven.di.module.BookFragmentModule;
import com.github.chengheaven.heaven.view.BaseFragment;
import com.github.chengheaven.heaven.presenter.BasePresenter;
import com.github.chengheaven.heaven.presenter.book.CulturePresenter;
import com.github.chengheaven.heaven.presenter.book.LifePresenter;
import com.github.chengheaven.heaven.presenter.book.LiteraturePresenter;
import com.github.chengheaven.heaven.presenter.mainFragment.BookContract;
import com.github.chengheaven.heaven.view.main.book.CultureFragment;
import com.github.chengheaven.heaven.view.main.book.LifeFragment;
import com.github.chengheaven.heaven.view.main.book.LiteratureFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

public class BookFragment extends BaseFragment implements BookContract.View {

    @BindView(R.id.viewpager_gank)
    ViewPager mViewpager;
    @BindView(R.id.tab_gank)
    TabLayout mTab;

    @Inject
    LiteraturePresenter mLiteraturePresenter;

    @Inject
    CulturePresenter mCulturePresenter;

    @Inject
    LifePresenter mLifePresenter;

    private BookContract.Presenter mPresenter;

    public static BookFragment newInstance() {
        return new BookFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_gank, container, false);
        ButterKnife.bind(this, view);

        List<Fragment> fragments = new ArrayList<>();
        LiteratureFragment literatureFragment = LiteratureFragment.newInstance("文学");
        CultureFragment cultureFragment = CultureFragment.newInstance();
        LifeFragment lifeFragment = LifeFragment.newInstance("生活");

        fragments.add(literatureFragment);
        fragments.add(cultureFragment);
        fragments.add(lifeFragment);

        List<String> titles = new ArrayList<>();
        titles.add("文学");
        titles.add("文化");
        titles.add("生活");

        TabPagerAdapter adapter = new TabPagerAdapter(getChildFragmentManager(), fragments, titles);
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(2);
        mTab.setupWithViewPager(mViewpager);
        mTab.setTabMode(TabLayout.MODE_FIXED);

        DaggerBookComponent.builder()
                .dataRepositoryComponent(((App) getActivity().getApplication()).getDataRepositoryComponent())
                .bookFragmentModule(new BookFragmentModule(
                        literatureFragment,
                        cultureFragment,
                        lifeFragment
                ))
                .build()
                .inject(this);

        mPresenter.start();

        return view;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (BookContract.Presenter) presenter;
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
