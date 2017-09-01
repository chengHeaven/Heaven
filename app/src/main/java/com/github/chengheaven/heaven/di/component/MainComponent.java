package com.github.chengheaven.heaven.di.component;

import com.github.chengheaven.heaven.di.FragmentScoped;
import com.github.chengheaven.heaven.di.module.MainPresenterModule;
import com.github.chengheaven.heaven.view.main.MainActivity;

import dagger.Component;

/**
 * @author Heaven・Cheng Created on 17/4/19.
 */

@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = MainPresenterModule.class)
public interface MainComponent {

    void inject(MainActivity activity);
}
