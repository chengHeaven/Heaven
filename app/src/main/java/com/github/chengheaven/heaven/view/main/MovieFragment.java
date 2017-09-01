package com.github.chengheaven.heaven.view.main;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.bean.MovieBean;
import com.github.chengheaven.heaven.customer.XRecyclerView.XRecyclerView;
import com.github.chengheaven.heaven.helper.BaseFragment;
import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.presenter.mainFragment.MovieContract;
import com.github.chengheaven.heaven.view.movieDetail.MovieDetailActivity;
import com.github.chengheaven.heaven.view.movieTop.MovieTopActivity;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

public class MovieFragment extends BaseFragment implements MovieContract.View {

    @BindView(R.id.movie_recycler)
    XRecyclerView mRecycler;
    @BindView(R.id.image_loading)
    ImageView mLoadingImage;
    @BindView(R.id.ll_loading_bar)
    LinearLayout mLoadingBar;
    @BindView(R.id.ll_error_refresh)
    LinearLayout mErrorBar;

    private AnimationDrawable mAnimationDrawable;

    private MovieAdapter mAdapter;

    private MovieContract.Presenter mPresenter;

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.title_douban, container, false);
        ButterKnife.bind(this, view);

        showLoading();
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setNestedScrollingEnabled(false);
        mRecycler.setPullRefreshEnabled(false);
        mRecycler.setLoadingMoreEnabled(false);
        mAdapter = new MovieAdapter();
        mRecycler.setAdapter(mAdapter);

        View headView = View.inflate(getContext(), R.layout.movie_header, null);
        new HeaderView(headView);
        mRecycler.addHeaderView(headView);

        mPresenter.getMovieList();

        return view;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (MovieContract.Presenter) presenter;
    }

    @Override
    public void showLoading() {
        mLoadingBar.setVisibility(View.VISIBLE);
        mAnimationDrawable = (AnimationDrawable) mLoadingImage.getDrawable();
        mAnimationDrawable.start();
    }

    @Override
    public void hideLoading() {
        mRecycler.setVisibility(View.VISIBLE);
        mLoadingBar.setVisibility(View.GONE);
        mAnimationDrawable.stop();
    }

    @Override
    public void showError() {
        mLoadingBar.setVisibility(View.GONE);
        mAnimationDrawable.stop();
        mErrorBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        mErrorBar.setVisibility(View.GONE);
    }

    @Override
    public void update(List<MovieBean> beanList) {
        mAdapter.update(beanList);
    }

    class HeaderView {

        HeaderView(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.movie_top)
        public void onClick() {
            startActivity(new Intent(getActivity(), MovieTopActivity.class));
        }
    }


    class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

        private List<MovieBean> mList = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(getContext())
                    .load(mList.get(position).getImages().getLarge())
                    .asBitmap()
                    .fitCenter()
                    .into(holder.mImage);
            holder.mTitle.setText(mList.get(position).getTitle());
            StringBuilder directors = new StringBuilder();
            for (MovieBean.PersonBean person : mList.get(position).getDirectors()) {
                directors.append(person.getName()).append(", ");
            }
            holder.mDirector.setText(directors.toString().equals("") ? "" : directors.substring(0, directors.length() - 2));
            StringBuilder casts = new StringBuilder();
            for (MovieBean.PersonBean person : mList.get(position).getCasts()) {
                casts.append(person.getName()).append(" / ");
            }
            holder.mPerformer.setText(casts.toString().equals("") ? "" : casts.substring(0, casts.length() - 3));
            StringBuilder genres = new StringBuilder();
            for (String s : mList.get(position).getGenres()) {
                genres.append(s).append(" / ");
            }
            holder.mGenre.setText(String.format("类型：%s", genres.toString().equals("") ? "" : genres.substring(0, genres.length() - 3)));
            holder.mScore.setText(String.format("评分：%s", mList.get(position).getRating().getAverage()));

            if (position != mList.size() - 1) {
                holder.mDivider.setBackgroundResource(R.color.colorLineItem);
            }

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movieBean", mList.get(position));
                intent.putExtra("item", bundle);
                startActivity(intent);
            });

            ViewHelper.setScaleX(holder.itemView, 0.8f);
            ViewHelper.setScaleY(holder.itemView, 0.8f);
            ViewPropertyAnimator.animate(holder.itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
            ViewPropertyAnimator.animate(holder.itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();

        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        void update(List<MovieBean> beanList) {
            mList.clear();
            mList = beanList;
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.movie_item_image)
            ImageView mImage;
            @BindView(R.id.movie_item_title)
            TextView mTitle;
            @BindView(R.id.movie_item_director)
            TextView mDirector;
            @BindView(R.id.movie_item_performer)
            TextView mPerformer;
            @BindView(R.id.movie_item_genre)
            TextView mGenre;
            @BindView(R.id.movie_item_score)
            TextView mScore;
            @BindView(R.id.movie_divider_color)
            View mDivider;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
