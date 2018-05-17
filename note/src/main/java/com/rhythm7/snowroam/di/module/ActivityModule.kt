package com.rhythm7.snowroam.di.module

import com.rhythm7.snowroam.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * 所有使用Dagger2 Activity 都需要在这个类里注册
 * Created by Jaminchanks on 2018-03-16.
 */

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun getActivity0(): MainActivity
}
