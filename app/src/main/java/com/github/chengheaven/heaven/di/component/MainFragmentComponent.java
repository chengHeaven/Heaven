package com.github.chengheaven.heaven.di.component;

import com.github.chengheaven.heaven.di.FragmentScoped;
import com.github.chengheaven.heaven.di.module.MainFragmentPresenterModule;
import com.github.chengheaven.heaven.view.main.MainFragment;

import dagger.Component;

/**
 * @author Heaven・Cheng Created on 17/5/15.
 */

@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = MainFragmentPresenterModule.class)
public interface MainFragmentComponent {

    void inject(MainFragment fragment);
}