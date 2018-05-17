package com.rhythm7.core.base.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.rhythm7.core.base.mvp.BaseView
import com.rhythm7.core.base.mvp.RxAutoDisposable

/**
 * hasOptMenu = HasOptionsMenu 是否控制父Activity的fragment
 */
abstract class BaseFragment(private var hasOptMenu: Boolean = false) : Fragment(), RxAutoDisposable, BaseView {

    protected lateinit var mRootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(hasOptMenu)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(getLayoutId(), null)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        injectRouter()
        initViewAndEvent()
    }


    /**
     * 注入Atouter
     */
    private fun injectRouter() {
        ARouter.getInstance().inject(this)
    }



    open fun initPresenter() {}

    abstract fun initViewAndEvent()

    abstract fun getLayoutId(): Int
}