package com.github.chengheaven.heaven.di.component;

import com.github.chengheaven.heaven.di.FragmentScoped;
import com.github.chengheaven.heaven.di.module.BookFragmentModule;
import com.github.chengheaven.heaven.view.main.BookFragment;

import dagger.Component;

/**
 * @author Heaven・Cheng Created on 17/6/12.
 */
@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = BookFragmentModule.class)
public interface BookComponent {

    void inject(BookFragment fragment);
}
