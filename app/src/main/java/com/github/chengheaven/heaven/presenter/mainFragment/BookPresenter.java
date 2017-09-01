package com.github.chengheaven.heaven.presenter.mainFragment;

import com.github.chengheaven.heaven.data.DataRepository;

import javax.inject.Inject;

/**
 * @author Heaven・Cheng Created on 17/5/15.
 */

public class BookPresenter implements BookContract.Presenter {

    private final BookContract.View mView;
    private final DataRepository mDataRepository;

    @Inject
    BookPresenter(BookContract.View view, DataRepository dataRepository) {
        mView = view;
        mDataRepository = dataRepository;
    }

    @Inject
    @Override
    public void setupPresenterToView() {
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
