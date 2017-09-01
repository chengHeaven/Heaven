package com.github.chengheaven.heaven.presenter.mainFragment;

import com.github.chengheaven.heaven.bean.MovieBean;
import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.helper.BaseView;

import java.util.List;

/**
 * @author Heaven・Cheng Created on 17/5/15.
 */

public class MovieContract {

    public interface View extends BaseView {

        void update(List<MovieBean> beanList);
    }

    public interface Presenter extends BasePresenter {

        void getMovieList();
    }
}
