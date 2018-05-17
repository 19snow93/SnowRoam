package com.rhythm7.core.base.activity

import android.os.Bundle
import android.support.v4.app.Fragment

import javax.inject.Inject

import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector

abstract class BaseDaggerActivity : BaseActivity(), HasFragmentInjector, HasSupportFragmentInjector {

    var supportFragmentInjector: DispatchingAndroidInjector<Fragment>? = null
        @Inject set

    var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>? = null
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return supportFragmentInjector
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment>? {
        return frameworkFragmentInjector
    }
}