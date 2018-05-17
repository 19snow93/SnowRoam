package com.rhythm7.core.base.mvvm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rhythm7.core.base.mvp.RxAutoDisposable
import com.rhythm7.core.base.mvvm.status.Status

/**
 *
 * Created by Jaminchanks on 2018-05-14.
 */
open class BaseViewModel : ViewModel(), RxAutoDisposable {
    lateinit var status: MutableLiveData<Status>

}