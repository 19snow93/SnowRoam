package com.rhythm7.core.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.rhythm7.core.crash.CrashReportingTree
import com.rhythm7.core.utlis.Logger
import com.rhythm7.core.utlis.ext.popFromStack
import com.rhythm7.core.utlis.ext.pushToStack
import com.rhythm7.core.utlis.mAppContext
import com.squareup.leakcanary.LeakCanary
import dagger.android.support.DaggerApplication

/**
 * Created by Jaminchanks on 2018-03-16.
 */
public abstract class BaseApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        mAppContext = this
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
        // Normal app init code...

        if (isDebugMode()) {
            Logger.plant(Logger.DebugTree())
            ARouter.openLog()    // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        } else {
            Logger.plant(CrashReportingTree())
        }

        ARouter.init(this) // 尽可能早，推荐在Application中初始化

        registerActivityLifecycleCallbacks()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun registerActivityLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
                activity?.popFromStack()
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                activity?.pushToStack()
            }
        })
    }

    abstract fun isDebugMode(): Boolean
}