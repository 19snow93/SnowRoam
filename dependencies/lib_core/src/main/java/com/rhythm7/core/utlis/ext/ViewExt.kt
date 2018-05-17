package com.rhythm7.core.utlis.ext

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.rhythm7.core.utlis.Logger

/**
 * Created by Jaminchanks on 2018/3/29.
 */

/**
 * 设置组件左边drawable
 */
fun View.etDrawableLeft(drawableId: Int) {
    try {

        val drawableImg: Drawable
        val res = this.context.resources
        drawableImg = res.getDrawable(drawableId, null)
        //调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        drawableImg.setBounds(0, 0, drawableImg.minimumWidth, drawableImg.minimumHeight)

        (this as? Button)?.setCompoundDrawables(drawableImg, null, null, null)
        (this as? TextView)?.setCompoundDrawables(drawableImg, null, null, null)
        (this as? EditText)?.setCompoundDrawables(drawableImg, null, null, null)

    } catch (e: Exception) {
        Logger.d("etDrawableLeft", e)
    }

}


/**
 * 设置组件左边drawable
 */
fun View.setDrawableRight(drawableId: Int) {
    try {
        val drawableImg: Drawable
        val res = this.context.resources
        drawableImg = res.getDrawable(drawableId, null)
        //调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        drawableImg.setBounds(0, 0, drawableImg.minimumWidth, drawableImg.minimumHeight)

        (this as? Button)?.setCompoundDrawables(null, null, drawableImg, null)
        (this as? TextView)?.setCompoundDrawables(null, null, drawableImg, null)
        (this as? EditText)?.setCompoundDrawables(null, null, drawableImg, null)

    } catch (e: Exception) {
        Logger.d("setDrawableRight", e)
    }

}
