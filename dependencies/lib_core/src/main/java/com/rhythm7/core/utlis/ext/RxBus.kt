package com.rhythm7.core.utlis.ext

import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor

object RxBus  {
    private val mBus: FlowableProcessor<Any> by lazy { PublishProcessor.create<Any>().toSerialized() }

    fun post(obj: Any) {
        mBus.onNext(obj)
    }

    fun <T> register(clz: Class<T>): Flowable<T> {
        return mBus.ofType(clz)
    }

    fun unregisterAll() {
        //会将所有由mBus 生成的 Flowable 都置 completed 状态  后续的 所有消息  都收不到了
        mBus.onComplete()
    }

    fun hasSubscribers(): Boolean {
        return mBus.hasSubscribers()
    }

}