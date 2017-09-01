package com.github.chengheaven.heaven.presenter.movieTop;

import com.github.chengheaven.heaven.bean.MovieBean;
import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.helper.BaseView;

import java.util.List;

/**
 * @author Heaven・Cheng Created on 17/6/7.
 */

public class MovieTopContract {

    public interface View extends BaseView {

        void updateMovieTopList(List<MovieBean> movieList);

        void refreshMovieTopList(List<MovieBean> movieList);
    }

    public interface Presenter extends BasePresenter {

        void getMovieTopList(String type, int start, int count);
    }
}
