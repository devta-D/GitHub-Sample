package com.devta.githubsample.base;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.devta.githubsample.di.component.ApplicationComponent;
import com.devta.githubsample.di.component.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created on : May, 01, 2020 at 13:47
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class BaseApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent component =
                DaggerApplicationComponent.builder().application(this).build();
        component.inject(this);
        return component;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
