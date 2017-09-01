package com.github.chengheaven.heaven.view.main.book;

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
import com.github.chengheaven.heaven.bean.BookBean;
import com.github.chengheaven.heaven.customer.XRecyclerView.XRecyclerView;
import com.github.chengheaven.heaven.helper.BaseFragment;
import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.presenter.book.LifeContract;
import com.github.chengheaven.heaven.view.bookDetail.BookDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Heaven・Cheng Created on 17/6/12.
 */

public class LifeFragment extends BaseFragment implements LifeContract.View {

    @BindView(R.id.image_loading)
    ImageView mLoadingImage;
    @BindView(R.id.ll_loading_bar)
    LinearLayout mLoadingBar;
    @BindView(R.id.ll_error_refresh)
    LinearLayout mErrorBar;
    @BindView(R.id.book_fragment_recycler)
    XRecyclerView mBookRecycler;

    private AnimationDrawable mAnimationDrawable;

    private static final String TYPE = "param";
    private String mType = "综合";
    private int start = 0;
    private int count = 30;
    private LifeFragment.LifeAdapter mAdapter;

    private LifeContract.Presenter mPresenter;

    public static LifeFragment newInstance(String param) {
        LifeFragment fragment = new LifeFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_fragment, container, false);
        ButterKnife.bind(this, view);

        showLoading();
        mBookRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new LifeFragment.LifeAdapter();
        mBookRecycler.setAdapter(mAdapter);
        mBookRecycler.setPullRefreshEnabled(false);
        mBookRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                start = 0;
                mPresenter.getLifeList(getString(R.string.refresh), mType, start, count);
            }

            @Override
            public void onLoadMore() {
                start += count;
                mPresenter.getLifeList(null, mType, start, count);
            }
        });

        mPresenter.getLifeList(null, mType, start, count);

        return view;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (LifeContract.Presenter) presenter;
    }

    @Override
    public void showLoading() {
        mLoadingBar.setVisibility(View.VISIBLE);
        mAnimationDrawable = (AnimationDrawable) mLoadingImage.getDrawable();
        mAnimationDrawable.start();
    }

    @Override
    public void hideLoading() {
        mBookRecycler.setVisibility(View.VISIBLE);
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
    public void refreshLifeView(List<BookBean> results) {
        mBookRecycler.refreshComplete();
        mAdapter.refreshList(results);
    }

    @Override
    public void updateLifeView(List<BookBean> results) {
        mBookRecycler.refreshComplete();
        mAdapter.updateList(results);
    }

    class LifeAdapter extends RecyclerView.Adapter<LifeAdapter.ViewHolder> {

        private List<BookBean> mList = new ArrayList<>();

        void updateList(List<BookBean> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        void refreshList(List<BookBean> list) {
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
                    .asBitmap()
                    .placeholder(R.drawable.img_one_bi_one)
                    .fitCenter()
                    .into(holder.mBookImage);
            holder.mBookName.setText(mList.get(position).getTitle());
            holder.mBookScore.setText(String.format("评分：%s", mList.get(position).getRating().getAverage()));

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bookBean", mList.get(position));
                intent.putExtra("item", bundle);
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