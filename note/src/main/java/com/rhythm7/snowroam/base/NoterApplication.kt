package com.rhythm7.snowroam.base

import com.rhythm7.core.base.BaseApplication
import com.rhythm7.snowroam.BuildConfig
import com.rhythm7.snowroam.di.component.DaggerAppSubComponent
import dagger.android.AndroidInjector


/**
 * Created by Jaminchanks on 2018-03-14.
 */
public open class NoterApplication : BaseApplication(){

    override fun isDebugMode() = BuildConfig.DEBUG

    override fun applicationInjector(): AndroidInjector<NoterApplication> {
        return DaggerAppSubComponent.builder().create(this)
    }

}