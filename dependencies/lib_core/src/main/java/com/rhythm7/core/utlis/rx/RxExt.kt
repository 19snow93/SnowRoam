package com.rhythm7.core.utlis.rx

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler

/**
 * RxJava 的线程切换
 * Created by Jaminchanks on 2018-05-08.
 */

fun Completable.io_main(): Completable{
    return this.subscribeOn(IoScheduler())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T: Any> Flowable<T>.io_main(): Flowable<T> {
    return this.subscribeOn(IoScheduler())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T: Any> Single<T>.io_main(): Single<T> {
    return this.subscribeOn(IoScheduler())
            .observeOn(AndroidSchedulers.mainThread())
}

