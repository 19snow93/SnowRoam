package com.rhythm7.core.base.mvp

/**
 * Created by codeest on 2016/8/2.
 * Presenter基类
 */
interface BasePresenter<in V : BaseView> {

    fun attachView(view: V)

    fun detachView()
}
