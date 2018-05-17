package com.rhythm7.core.base.fragment

import android.content.Context
import android.support.v4.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseDaggerFragment(hasOptMenu: Boolean = false) : BaseFragment(hasOptMenu), HasSupportFragmentInjector {

    var childFragmentInjector: DispatchingAndroidInjector<Fragment>? = null
        @Inject set

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return childFragmentInjector
    }
}

