package com.rhythm7.core.base.activity

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.rhythm7.core.R
import com.rhythm7.core.base.mvp.BaseView
import com.rhythm7.core.base.mvp.RxAutoDisposable
import com.rhythm7.core.utlis.ext.resIdToColor
import com.rhythm7.core.utlis.ext.setStatusBarLightMode
import com.rhythm7.core.utlis.ext.setTranslucentWindows

/**
 * Created by Jaminchanks on 2018-03-14.
 */

abstract class BaseActivity : AppCompatActivity(), RxAutoDisposable, BaseView {
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTranslucentWindows()
        setStatusBarLightMode()

        injectRouter()
        setContentView(getLayoutId())
        initPresenter()
        initViewAndEvent()
    }

    /**
     * 注入Atouter
     */
    private fun injectRouter() {
        ARouter.getInstance().inject(this)
    }

    fun setToolbar(title: String = "", toolbar: Toolbar = findViewById(R.id.toolbar_custom)) {
        //假如使用的是标准的toolbar
        mToolbar = toolbar

        if (toolbar.id == R.id.toolbar_custom) {
            try {
                val tvTitle = toolbar.findViewById(R.id.tv_toolbar_title) as TextView
                tvTitle.visibility = View.VISIBLE
                tvTitle.text = title
            } catch (e: Exception) {
                throw RuntimeException("请设置R.layout.view_toolbar为标题栏")
            }
        }

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        //设置默认主题颜色
        setToolbarForeground(R.color.colorPrimary.resIdToColor())
    }


    fun getToolbar(): Toolbar {
        return mToolbar
    }

    fun setToolbarForeground(color: Int) {
        mToolbar.navigationIcon?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        mToolbar.findViewById<TextView>(R.id.tv_toolbar_title)?.setTextColor(color)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    open fun initPresenter() {}

    abstract fun initViewAndEvent()

    abstract fun getLayoutId(): Int
}
