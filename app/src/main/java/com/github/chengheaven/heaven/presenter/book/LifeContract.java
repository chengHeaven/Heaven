package com.github.chengheaven.heaven.presenter.book;

import com.github.chengheaven.heaven.bean.BookBean;
import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.helper.BaseView;

import java.util.List;

/**
 * @author Heaven・Cheng Created on 17/6/12.
 */

public class LifeContract {

    public interface View extends BaseView {

        void refreshLifeView(List<BookBean> results);

        void updateLifeView(List<BookBean> results);
    }

    public interface Presenter extends BasePresenter {

        void getLifeList(String type, String tag, int start, int count);
    }
}