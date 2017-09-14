package com.github.chengheaven.heaven.view.main.gank;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.bean.HomeBean;
import com.github.chengheaven.heaven.customer.XRecyclerView.XRecyclerView;
import com.github.chengheaven.heaven.view.BaseFragment;
import com.github.chengheaven.heaven.presenter.BasePresenter;
import com.github.chengheaven.heaven.presenter.gank.AndroidContract;
import com.github.chengheaven.heaven.view.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

public class AndroidFragment extends BaseFragment implements AndroidContract.View {

    @BindView(R.id.customization_recycler)
    XRecyclerView mRecycler;
    private int page = 1;
    private int per_page = 20;

    private AndroidAdapter mAdapter;

    private AndroidContract.Presenter mPresenter;

    public static AndroidFragment newInstance() {
        return new AndroidFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.customization_fragment, container, false);
        ButterKnife.bind(this, view);

        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new AndroidAdapter();
        mRecycler.setAdapter(mAdapter);

        mPresenter.getAndroid(null, getString(R.string.android), page, per_page);

        mRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.getAndroid(getString(R.string.refresh), getString(R.string.android), page, per_page);
            }

            @Override
            public void onLoadMore() {
                page++;
                mPresenter.getAndroid(null, getString(R.string.android), page, per_page);
            }
        });

        return view;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (AndroidContract.Presenter) presenter;
    }

    @Override
    public void updateList(List<HomeBean> beanList) {
        recyclerComplete();
        mAdapter.update(beanList);
    }

    @Override
    public void refreshList(List<HomeBean> beanList) {
        recyclerComplete();
        mAdapter.refresh(beanList);
    }

    @Override
    public void recyclerComplete() {
        mRecycler.refreshComplete();
    }

    class AndroidAdapter extends RecyclerView.Adapter<AndroidAdapter.ViewHolder> {

        private List<HomeBean> mList = new ArrayList<>();

        @Override
        public AndroidAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customization_item, parent, false);
            return new AndroidAdapter.ViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void onBindViewHolder(AndroidAdapter.ViewHolder holder, int position) {
            holder.mWelfare.setVisibility(View.GONE);
            holder.mImage.setVisibility(View.GONE);
            if (mList.get(position).getImages() != null) {
                holder.mImage.setVisibility(View.VISIBLE);

                /*
                 * 加载 GIF url + ?imageView2/0/w/width/h/height
                 * width 请求图片宽度
                 * height 请求图片高度
                 */

                Glide.with(getActivity())
                        .load(mList.get(position).getImages().get(0))
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.drawable.img_two_bi_one)
                        .error(R.drawable.img_two_bi_one)
                        .centerCrop()
                        .into(holder.mImage);
            }
            holder.mTitle.setText(mList.get(position).getDesc());
            holder.mWho.setText(mList.get(position).getWho());
            holder.mType.setText(mList.get(position).getType());
            holder.mDate.setText(getDate(mList.get(position).getPublishedAt()));

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("mUrl", mList.get(position).getUrl());
                intent.putExtra("mTitle", getActivity().getResources().getString(R.string.loading));
                getActivity().startActivity(intent);
            });
        }

        String getDate(String date) {
            String[] s = date.split("-");
            date = s[1] + "-" + s[2].substring(0, 2);
            return date;
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        void update(List<HomeBean> beanList) {
            mList.addAll(beanList);
            notifyDataSetChanged();
        }

        void refresh(List<HomeBean> beanList) {
            mList.clear();
            mList = beanList;
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.customization_title)
            TextView mTitle;
            @BindView(R.id.customization_image)
            ImageView mImage;
            @BindView(R.id.customization_welfare)
            ImageView mWelfare;
            @BindView(R.id.customization_who)
            TextView mWho;
            @BindView(R.id.customization_type)
            TextView mType;
            @BindView(R.id.customization_date)
            TextView mDate;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
