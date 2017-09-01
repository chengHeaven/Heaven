package com.github.chengheaven.heaven.data.every;

import com.github.chengheaven.heaven.bean.HomeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Heaven・Cheng Created on 17/4/19.
 */

public interface EveryDataSource {

    interface EveryCallback<T> {

        void onSuccess(List<T> results);

        void onFailed(String msg);
    }

    interface EveryContentCallback<T> {

        void onSuccess(ArrayList<List<T>> results);

        void onFailed(String msg);
    }

    void getBanner(EveryCallback callback);

    void getRecycler(String year, String month, String day, EveryContentCallback<HomeBean> callback);

    void getGankData(String id, int page, int pre_page, EveryCallback callback);

    void setImageUrlsLocal(List<String> urls);

    List<String> getImageUrlsLocal();

    void clear();
}
