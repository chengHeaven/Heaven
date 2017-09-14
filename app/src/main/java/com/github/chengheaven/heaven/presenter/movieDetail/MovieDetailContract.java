package com.github.chengheaven.heaven.presenter.movieDetail;

import com.github.chengheaven.heaven.bean.MovieBean;
import com.github.chengheaven.heaven.bean.MovieDetailBean;
import com.github.chengheaven.heaven.presenter.BasePresenter;
import com.github.chengheaven.heaven.view.BaseView;

/**
 * @author Heaven・Cheng Created on 17/6/7.
 */

public class MovieDetailContract {

    public interface View extends BaseView {

        void updateTitleView(MovieBean result);

        void updateView(MovieDetailBean result);
    }

    public interface Presenter extends BasePresenter {

        void getMovieDetail();
    }
}
