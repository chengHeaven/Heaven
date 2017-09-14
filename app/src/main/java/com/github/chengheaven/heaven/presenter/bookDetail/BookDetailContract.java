package com.github.chengheaven.heaven.presenter.bookDetail;

import com.github.chengheaven.heaven.bean.BookBean;
import com.github.chengheaven.heaven.presenter.BasePresenter;
import com.github.chengheaven.heaven.view.BaseView;

/**
 * @author Heaven・Cheng Created on 17/6/13.
 */

public class BookDetailContract {

    public interface View extends BaseView {

        void updateTitleView(BookBean result);

        void updateView(BookBean result);
    }

    public interface Presenter extends BasePresenter {

        void getBookDetailList();
    }
}
