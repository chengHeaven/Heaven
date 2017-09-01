package com.github.chengheaven.heaven.data.book;

import com.github.chengheaven.heaven.bean.BookBean;

import java.util.List;

/**
 * @author Heaven・Cheng Created on 17/6/12.
 */

public interface BookDataSource {

    interface BookCallback<T> {

        void onSuccess(List<T> results);

        void onFailed(String message);
    }

    interface BookDetailCallback {

        void onSuccess(BookBean bookBean);

        void onFailed(String message);
    }

    void getBookData(String tag, int start, int count, BookCallback callback);

    void getBookDetailData(String id, BookDetailCallback callback);

}
