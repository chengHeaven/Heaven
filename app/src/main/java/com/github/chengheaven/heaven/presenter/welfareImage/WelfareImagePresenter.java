package com.github.chengheaven.heaven.presenter.welfareImage;

import com.github.chengheaven.heaven.data.DataRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Heaven・Cheng Created on 17/5/9.
 */

public class WelfareImagePresenter implements WelfareImageContract.Presenter {

    private final DataRepository mDataRepository;
    private final WelfareImageContract.View mView;
    private int mPosition;

    @Inject
    WelfareImagePresenter(DataRepository mDataRepository, WelfareImageContract.View mView) {
        this.mDataRepository = mDataRepository;
        this.mView = mView;
    }

    @Inject
    @Override
    public void setupPresenterToView() {
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void setPosition(int position) {
        mPosition = position;
    }

    @Override
    public void initViewPager() {
        mView.updateViewPager(getWelfare());
        mView.setIndicator(getWelfare().size());
        mView.setCurrentItemViewPager(mPosition);
    }

    public List<String> getWelfare() {
        return deepCopy(mDataRepository.getImageUrlsLocal());
    }

    private <T> List<T> deepCopy(List<T> src) {

        List<T> dest = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out;
            out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            //noinspection unchecked
            dest = (List<T>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dest;
    }
}
