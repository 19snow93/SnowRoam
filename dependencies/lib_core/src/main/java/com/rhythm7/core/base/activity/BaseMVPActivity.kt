package com.rhythm7.core.base.activity


import com.rhythm7.core.base.mvp.BaseView
import com.rhythm7.core.base.mvp.RxPresenter
import javax.inject.Inject

/**
 * Created by Jaminchanks on 2018-03-15.
 */

public abstract class BaseMVPActivity<V: BaseView , P: RxPresenter<V>> : BaseDaggerActivity(){
    @Inject
    public lateinit var mPresenter: P

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
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