package com.github.chengheaven.heaven.di.component;

import com.github.chengheaven.heaven.di.FragmentScoped;
import com.github.chengheaven.heaven.di.module.BookDetailPresenterModule;
import com.github.chengheaven.heaven.view.bookDetail.BookDetailActivity;

import dagger.Component;

/**
 * @author Heaven・Cheng Created on 17/6/13.
 */

@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = BookDetailPresenterModule.class)
public interface BookDetailComponent {

    void inject(BookDetailActivity activity);
}
