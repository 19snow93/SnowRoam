package com.rhythm7.core.base.mvp

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatDialog
import android.widget.Toast

/**
 * Created by codeest on 2016/8/2.
 * View基类
 */
interface BaseView {

    private val mProgressDialog: AppCompatDialog
        get() = when{
            this is Activity -> AppCompatDialog(this)
            this is Fragment -> AppCompatDialog(this.activity)
            else -> throw RuntimeException("BaseView 目前仅支持被Activity或者Fragment继承")
        }

    fun showLoadingDialog() {
        try {
            mProgressDialog.show()
        } catch (_: Exception) {
        }
    }

    fun dismissLoadingDialog() {
        if (mProgressDialog.isShowing)
            mProgressDialog.dismiss()
    }

    fun showToast(msg: String) {
        when {
            this is Activity -> Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            this is Fragment -> Toast.makeText(this.activity, msg, Toast.LENGTH_SHORT).show()
        }
    }
}
