package com.github.chengheaven.heaven.data;

import com.github.chengheaven.heaven.bean.HomeBean;
import com.github.chengheaven.heaven.data.book.BookDataSource;
import com.github.chengheaven.heaven.data.every.EveryDataSource;
import com.github.chengheaven.heaven.data.movie.MovieDataSource;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Heaven・Cheng Created on 17/4/19.
 */

public class DataRepository implements EveryDataSource, MovieDataSource, BookDataSource {

    private final EveryDataSource mEveryDataSource;

    private final MovieDataSource mMovieDataSource;

    private final BookDataSource mBookDataSource;

    @Inject
    DataRepository(EveryDataSource everyDataSource, MovieDataSource movieDataSource, BookDataSource bookDataSource) {
        this.mEveryDataSource = everyDataSource;
        this.mMovieDataSource = movieDataSource;
        mBookDataSource = bookDataSource;
    }

    @Override
    public void getBanner(EveryCallback callback) {
        mEveryDataSource.getBanner(callback);
    }

    @Override
    public void getRecycler(String year, String month, String day, EveryContentCallback<HomeBean> callback) {
        mEveryDataSource.getRecycler(year, month, day, callback);
    }

    @Override
    public void getGankData(String id, int page, int pre_page, EveryCallback callback) {
        mEveryDataSource.getGankData(id, page, pre_page, callback);
    }

    @Override
    public void setImageUrlsLocal(List<String> urls) {
        mEveryDataSource.setImageUrlsLocal(urls);
    }

    @Override
    public List<String> getImageUrlsLocal() {
        return mEveryDataSource.getImageUrlsLocal();
    }

    @Override
    public void clear() {
        mEveryDataSource.clear();
    }

    @Override
    public void getHotMovie(MovieCallback callback) {
        mMovieDataSource.getHotMovie(callback);
    }

    @Override
    public void getMovieTop(int start, int count, MovieCallback callback) {
        mMovieDataSource.getMovieTop(start, count, callback);
    }

    @Override
    public void getMovieDetail(String id, MovieDetailCallback callback) {
        mMovieDataSource.getMovieDetail(id, callback);
    }

    @Override
    public void getBookData(String tag, int start, int count, BookCallback callback) {
        mBookDataSource.getBookData(tag, start, count, callback);
    }

    @Override
    public void getBookDetailData(String id, BookDetailCallback callback) {
        mBookDataSource.getBookDetailData(id, callback);
    }
}
