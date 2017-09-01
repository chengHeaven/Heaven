package com.github.chengheaven.heaven.view.movieTop;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.bean.MovieBean;
import com.github.chengheaven.heaven.customer.XRecyclerView.XRecyclerView;
import com.github.chengheaven.heaven.helper.BaseFragment;
import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.presenter.movieTop.MovieTopContract;
import com.github.chengheaven.heaven.view.movieDetail.MovieDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Heaven・Cheng Created on 17/6/7.
 */

public class MovieTopFragment extends BaseFragment implements MovieTopContract.View {

    @BindView(R.id.image_loading)
    ImageView mLoadingImage;
    @BindView(R.id.ll_loading_bar)
    LinearLayout mLoadingBar;
    @BindView(R.id.ll_error_refresh)
    LinearLayout mErrorBar;
    @BindView(R.id.book_fragment_recycler)
    XRecyclerView mMovieRecycler;

    private int start = 0;
    private int count = 30;

    private MovieTopAdapter mAdapter;
    private AnimationDrawable mAnimationDrawable;
    private MovieTopContract.Presenter mPresenter;

    public static MovieTopFragment newInstance() {
        return new MovieTopFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_fragment, container, false);
        ButterKnife.bind(this, view);

        showLoading();
        mMovieRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new MovieTopAdapter();
        mMovieRecycler.setAdapter(mAdapter);
        mMovieRecycler.setPullRefreshEnabled(false);
        mMovieRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                start = 0;
                mPresenter.getMovieTopList(getString(R.string.refresh), start, count);
            }

            @Override
            public void onLoadMore() {
                start += count;
                mPresenter.getMovieTopList(null, start, count);
            }
        });

        mPresenter.getMovieTopList(null, start, count);

        return view;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (MovieTopContract.Presenter) presenter;
    }

    @Override
    public void showLoading() {
        mLoadingBar.setVisibility(View.VISIBLE);
        mAnimationDrawable = (AnimationDrawable) mLoadingImage.getDrawable();
        mAnimationDrawable.start();
    }

    @Override
    public void hideLoading() {
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
    public void updateMovieTopList(List<MovieBean> movieList) {
        mMovieRecycler.refreshComplete();
        mAdapter.updateList(movieList);
    }

    @Override
    public void refreshMovieTopList(List<MovieBean> movieList) {
        mMovieRecycler.refreshComplete();
        mAdapter.refreshList(movieList);
    }

    class MovieTopAdapter extends RecyclerView.Adapter<MovieTopAdapter.ViewHolder> {

        private List<MovieBean> mList = new ArrayList<>();

        void updateList(List<MovieBean> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        void refreshList(List<MovieBean> list) {
            mList.clear();
            mList = list;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_fragment_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(getContext())
                    .load(mList.get(position).getImages().getLarge())
                    .placeholder(R.drawable.img_one_bi_one)
                    .crossFade(1500)
                    .centerCrop()
                    .into(holder.mBookImage);
            holder.mBookName.setText(mList.get(position).getTitle());
            holder.mBookScore.setText(String.format("评分：%s", mList.get(position).getRating().getAverage()));

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra("id", mList.get(position).getId());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.book_item_image)
            ImageView mBookImage;
            @BindView(R.id.book_item_name)
            TextView mBookName;
            @BindView(R.id.book_item_score)
            TextView mBookScore;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
