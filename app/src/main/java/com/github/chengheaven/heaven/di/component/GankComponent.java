package com.github.chengheaven.heaven.di.component;

import com.github.chengheaven.heaven.di.FragmentScoped;
import com.github.chengheaven.heaven.di.module.GankFragmentModule;
import com.github.chengheaven.heaven.view.main.GankFragment;

import dagger.Component;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = GankFragmentModule.class)
public interface GankComponent {

    void inject(GankFragment fragment);
}
