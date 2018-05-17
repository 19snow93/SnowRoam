package com.rhythm7.core.utlis.ext

import android.content.res.Resources
import android.support.v4.content.ContextCompat
import com.rhythm7.core.utlis.ResourceUtil
import com.rhythm7.core.utlis.mAppContext


fun getResources(): Resources {
    return mAppContext.resources
}

/**
 * colorResId 转color
 */
fun Int.resIdToColor() = ContextCompat.getColor(mAppContext, this)

/**
 * drawableResId 转drawable
 */
fun Int.resIdTooDrawable() = ContextCompat.getDrawable(mAppContext, this)

/**
 * dimensResId 转 dimens
 */
fun Int.resIdToDimens() = getResources().getDimension(this)

/**
 * 获取字符串数组
 */
fun Int.resIdToStringArray(): Array<String> =  getResources().getStringArray(this)


/**
 * 获取string.xml下的字符串资源
 *
 * @param resId
 * @param formatArgs
 * @return
 */
fun Int.resIdToString(vararg formatArgs: Any): String {
    return getResources().getString(this, *formatArgs)
}


/**
 * 获取string.xml下的字符串资源
 * @param formatArgs
 * @return
 */
fun Int.resIdTogetString(vararg formatArgs: Any): String = getResources().getString(this, *formatArgs)

