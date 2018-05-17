package com.rhythm7.core.base.mvvm

import com.rhythm7.core.base.mvvm.status.Status
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * Created by Jaminchanks on 2018-05-15.
 */
abstract class BaseStatusSubscribe<T>(val viewModel: BaseViewModel) : Subscriber<T> {
    override fun onComplete() {
        viewModel.status.value = Status.SUCCESS
    }

    override fun onSubscribe(s: Subscription?) {
        viewModel.status.value = Status.LOADING
    }


    override fun onError(t: Throwable?) {
        viewModel.status.value = Status.ERROR

    }

}