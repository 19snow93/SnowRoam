package com.rhythm7.core.utlis.delegates

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.app.AppCompatActivity
import com.rhythm7.core.base.mvvm.BaseViewModel
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Jaminchanks on 2018-05-14.
 */
class ActivityAutoDisposable <T : BaseViewModel>(val activity: AppCompatActivity) : ReadWriteProperty<AppCompatActivity, T> {
    private var _value: T? = null

    init {
        activity.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                _value?.clearAllDisposable()
                _value = null
            }
        })
    }

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        return _value ?: throw IllegalStateException(
                "should never call auto-cleared-value get when it might not be available"
        )
    }

    override fun setValue(thisRef: AppCompatActivity, property: KProperty<*>, value: T) {
        _value = value
    }
}

/**
 * Creates an [AutoClearedValue] associated with this activity.
 */
fun <T: BaseViewModel> AppCompatActivity.AutoDisposable() = ActivityAutoDisposable<T>(this)