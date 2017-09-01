package com.github.chengheaven.heaven.di.component;

import com.github.chengheaven.heaven.di.FragmentScoped;
import com.github.chengheaven.heaven.di.module.MovieTopPresenterModule;
import com.github.chengheaven.heaven.view.movieTop.MovieTopActivity;

import dagger.Component;

/**
 * @author Heaven・Cheng Created on 17/6/7.
 */

@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = MovieTopPresenterModule.class)
public interface MovieTopComponent {

    void inject(MovieTopActivity activity);
}
