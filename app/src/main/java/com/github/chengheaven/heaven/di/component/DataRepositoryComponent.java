package com.github.chengheaven.heaven.di.component;

import com.github.chengheaven.heaven.data.DataRepository;
import com.github.chengheaven.heaven.di.module.ApplicationModule;
import com.github.chengheaven.heaven.di.module.DataRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Heaven・Cheng Created on 17/4/19.
 */

@Singleton
@Component(modules = {DataRepositoryModule.class, ApplicationModule.class})
public interface DataRepositoryComponent {

    DataRepository getRepository();
}
