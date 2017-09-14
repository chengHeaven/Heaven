package com.github.chengheaven.heaven.view.main.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.customer.DividerItemDecoration;
import com.github.chengheaven.heaven.customer.DragSortRecycler;
import com.github.chengheaven.heaven.view.BaseActivity;
import com.github.chengheaven.heaven.tools.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Heaven・Cheng Created on 17/9/4.
 */

public class ItemChangeActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.item_change_recycler)
    RecyclerView mItemChangeRecycler;

    private ItemChangeAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.item_change_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mToolbar.setTitle(getString(R.string.change_item));

        mItemChangeRecycler.setLayoutManager(new LinearLayoutManager(this));
        mItemChangeRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        String strItem = SharedPreferenceUtil.getInstance(this).getItemPosition();
        List<String> list = new ArrayList<>();
        Collections.addAll(list, strItem.split(" "));
        mAdapter = new ItemChangeAdapter(list);
        mItemChangeRecycler.setAdapter(mAdapter);

        DragSortRecycler dragSortRecycler = new DragSortRecycler();
        dragSortRecycler.setViewHandleId(R.id.item_change_text);

        dragSortRecycler.setOnItemMovedListener((from, to) -> {
            Log.d("queue", "onItemMoved " + from + " to " + to);
            final String str = mAdapter.getItem(from);
            mAdapter.removeItem(from);
            mAdapter.addItem(to, str);
            mAdapter.notifyDataSetChanged();

            String st = "";
            for (int i = 0; i < mAdapter.mList.size(); i++) {
                if (i == mAdapter.mList.size() - 1) {
                    st = st + mAdapter.mList.get(i);
                    continue;
                }
                st = st + mAdapter.mList.get(i) + " ";
            }
            SharedPreferenceUtil.getInstance(this).setItemPosition(st);
        });

        mItemChangeRecycler.addItemDecoration(dragSortRecycler);
        mItemChangeRecycler.addOnItemTouchListener(dragSortRecycler);
        mItemChangeRecycler.addOnScrollListener(dragSortRecycler.getScrollListener());
        mItemChangeRecycler.setHasFixedSize(true);
    }

    class ItemChangeAdapter extends RecyclerView.Adapter<ItemChangeAdapter.ViewHolder> {

        private List<String> mList = new ArrayList<>();

        ItemChangeAdapter(List<String> mList) {
            this.mList = mList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_change_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mChangeItem.setText(mList.get(position));
        }

        String getItem(int i) {
            return mList.get(i);
        }

        void addItem(int i, String str) {
            mList.add(i, str);
        }

        void removeItem(int i) {
            mList.remove(i);
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.item_change_text)
            TextView mChangeItem;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected boolean isDoubleExit() {
        return false;
    }
}
