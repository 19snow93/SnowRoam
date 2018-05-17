package com.rhythm7.core.utlis.ext

import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Created by Jaminchanks on 2018-05-11.
 */

/**
 * 导航至某个activity
 */
fun navigateToActivity(path: String, withExtAction: (postcard: Postcard)->Unit = {}) {
    ARouter.getInstance().build(path).apply {
        withExtAction(this)
    }.navigation()
}

/**
 * 获得某个fragment
 */
fun <T: Fragment> navigateToFragment(path: String, withExtAction: (postcard: Postcard)->Unit = {}): T{
    @Suppress("UNCHECKED_CAST")
    return ARouter.getInstance().build(path).apply { withExtAction(this) }.navigation() as T
}

