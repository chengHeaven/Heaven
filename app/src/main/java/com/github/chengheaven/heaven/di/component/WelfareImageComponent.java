package com.github.chengheaven.heaven.di.component;

import com.github.chengheaven.heaven.di.FragmentScoped;
import com.github.chengheaven.heaven.di.module.WelfareImagePresenterModule;
import com.github.chengheaven.heaven.view.welfareImage.WelfareImageActivity;

import dagger.Component;

/**
 * @author Heaven・Cheng Created on 17/5/9.
 */

@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = WelfareImagePresenterModule.class)
public interface WelfareImageComponent {

    void inject(WelfareImageActivity activity);
}
