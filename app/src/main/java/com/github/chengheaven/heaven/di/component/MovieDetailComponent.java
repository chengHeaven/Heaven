package com.github.chengheaven.heaven.di.component;

import com.github.chengheaven.heaven.di.FragmentScoped;
import com.github.chengheaven.heaven.di.module.MovieDetailPresenterModule;
import com.github.chengheaven.heaven.view.movieDetail.MovieDetailActivity;

import dagger.Component;

/**
 * @author Heaven・Cheng Created on 17/6/7.
 */
@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = MovieDetailPresenterModule.class)
public interface MovieDetailComponent {

    void inject(MovieDetailActivity activity);
}
