package com.rhythm7.core.base.fragment

import com.rhythm7.core.base.mvp.BaseView
import com.rhythm7.core.base.mvp.RxPresenter
import javax.inject.Inject

abstract class BaseMVPFragment<V: BaseView, P: RxPresenter<V>>(hasOptMenu: Boolean = false)
    : BaseDaggerFragment(hasOptMenu) {

    @Inject
    public lateinit var mPresenter: P

    override fun onDetach() {
        mPresenter.detachView()
        super.onDetach()
    }

    override fun initPresenter() {
        try {
            @Suppress("UNCHECKED_CAST")
            mPresenter.attachView(this as V)
        } catch (e : ClassCastException) {
            throw RuntimeException("MVP类没有实现对应的View接口")
        }
    }
}