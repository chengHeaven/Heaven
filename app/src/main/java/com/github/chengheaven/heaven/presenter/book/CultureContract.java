package com.github.chengheaven.heaven.presenter.book;

import com.github.chengheaven.heaven.bean.BookBean;
import com.github.chengheaven.heaven.presenter.BasePresenter;
import com.github.chengheaven.heaven.view.BaseView;

import java.util.List;

/**
 * @author Heaven・Cheng Created on 17/6/12.
 */

public class CultureContract {

    public interface View extends BaseView {

        void refreshCultureView(List<BookBean> results);

        void updateCultureView(List<BookBean> results);
    }

    public interface Presenter extends BasePresenter {

        void getCultureList(String type, String tag, int start, int count);
    }
}
