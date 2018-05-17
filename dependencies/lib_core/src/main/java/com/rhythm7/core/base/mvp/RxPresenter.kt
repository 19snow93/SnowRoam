package com.rhythm7.core.base.mvp

/**
 * Created by codeest on 2016/8/2.
 * 基于Rx的Presenter封装,控制订阅的生命周期
 */
open class RxPresenter<V : BaseView> : BasePresenter<V>, RxAutoDisposable {

    protected var mView: V? = null

    override fun attachView(view: V) {
        this.mView = view
    }

    override fun detachView() {
        this.mView = null
        clearAllDisposable()
    }
}
