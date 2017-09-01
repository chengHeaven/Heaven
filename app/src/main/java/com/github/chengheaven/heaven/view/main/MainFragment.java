package com.github.chengheaven.heaven.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.application.App;
import com.github.chengheaven.heaven.di.component.DaggerMainFragmentComponent;
import com.github.chengheaven.heaven.di.module.MainFragmentPresenterModule;
import com.github.chengheaven.heaven.helper.BaseFragment;
import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.presenter.main.MainContract;
import com.github.chengheaven.heaven.presenter.mainFragment.BookPresenter;
import com.github.chengheaven.heaven.presenter.mainFragment.GankPresenter;
import com.github.chengheaven.heaven.presenter.mainFragment.MoviePresenter;
import com.github.chengheaven.heaven.view.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Heaven・Cheng Created on 17/4/20.
 */

public class MainFragment extends BaseFragment implements MainContract.View {

    @BindView(R.id.ll_title_menu)
    FrameLayout mTitleMenu;
    @BindView(R.id.title_gank)
    ImageView mTitleGank;
    @BindView(R.id.title_movie)
    ImageView mTitleMovie;
    @BindView(R.id.title_book)
    ImageView mTitleBook;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    @Inject
    BookPresenter mBookPresenter;

    @Inject
    GankPresenter mGankPresenter;

    @Inject
    MoviePresenter mMoviePresenter;

    private MainContract.Presenter mPresenter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, view);

        List<Fragment> fragments = new ArrayList<>();
        GankFragment gankFragment = GankFragment.newInstance();
        MovieFragment movieFragment = MovieFragment.newInstance();
        BookFragment bookFragment = BookFragment.newInstance();
        fragments.add(gankFragment);
        fragments.add(movieFragment);
        fragments.add(bookFragment);

        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(), fragments);
        mViewpager.setAdapter(adapter);

        DaggerMainFragmentComponent.builder()
                .dataRepositoryComponent(((App) getActivity().getApplication()).getDataRepositoryComponent())
                .mainFragmentPresenterModule(new MainFragmentPresenterModule(
                        gankFragment,
                        movieFragment,
                        bookFragment
                ))
                .build()
                .inject(this);

        mPresenter.start();

        return view;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (MainContract.Presenter) presenter;
    }

    @OnClick({R.id.ll_title_menu,
            R.id.title_gank,
            R.id.title_movie,
            R.id.title_book,
            R.id.ll_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_menu:
                ((MainActivity) getActivity()).mDrawer.openDrawer(Gravity.START);
                break;
            case R.id.title_gank:
                mViewpager.setCurrentItem(0);
                break;
            case R.id.title_movie:
                mViewpager.setCurrentItem(1);
                break;
            case R.id.title_book:
                mViewpager.setCurrentItem(2);
                break;
            case R.id.ll_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void setCurrentItem(int position) {
        mViewpager.setCurrentItem(position);
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
