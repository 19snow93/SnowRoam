package com.rhythm7.snowroam.di.component

import com.rhythm7.core.di.module.ViewModelFactoryModule
import com.rhythm7.snowroam.base.NoterApplication
import com.rhythm7.snowroam.di.module.ActivityModule
import com.rhythm7.snowroam.di.module.DataBaseModule
import com.rhythm7.snowroam.di.module.FragmentModule
import com.rhythm7.snowroam.di.module.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by jaminchanks on 2017/3/7.
 */

@Singleton
@Component(modules = [(DataBaseModule::class),
    (ActivityModule::class),
    (FragmentModule::class),
    (AndroidSupportInjectionModule::class),
    (ViewModelFactoryModule::class),
    (ViewModelModule::class)])
interface AppSubComponent : AndroidInjector<NoterApplication> {
    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<NoterApplication>()
}