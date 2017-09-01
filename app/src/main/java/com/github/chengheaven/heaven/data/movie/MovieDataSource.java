package com.github.chengheaven.heaven.data.movie;

import com.github.chengheaven.heaven.bean.MovieDetailBean;

import java.util.List;

/**
 * @author Heaven・Cheng Created on 17/5/16.
 */

public interface MovieDataSource {

    interface MovieCallback<T> {

        void onSuccess(List<T> results);

        void onFailed(String msg);
    }

    interface MovieDetailCallback {

        void onSuccess(MovieDetailBean bean);

        void onFailed(String msg);
    }

    void getHotMovie(MovieCallback callback);

    void getMovieTop(int start, int count, MovieCallback callback);

    void getMovieDetail(String id, MovieDetailCallback callback);
}
