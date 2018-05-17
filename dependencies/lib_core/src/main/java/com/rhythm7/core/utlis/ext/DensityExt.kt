package com.rhythm7.core.utlis.ext

import com.rhythm7.core.utlis.mAppContext

/**
 * Created by Jaminchanks on 2018-03-14.
 */
/**
 * 像素尺寸转换工具类
 * Created by chenmingzhen on 16-5-31.
 */
/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun Float.dp2px(): Int {
    val scale = mAppContext.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun Float.px2dp(): Int {
    val scale = mAppContext.resources.displayMetrics.density
    return (this / scale + 0.5f).toInt()
}


/**
 * 将px值转换为sp值，保证文字大小不变
 * @return
 */
fun Float.px2sp(): Int {
    val fontScale = mAppContext.resources.displayMetrics.scaledDensity
    return (this / fontScale + 0.5f).toInt()
}

/**
 * 将sp值转换为px值，保证文字大小不变
 * @return
 */
fun Float.sp2px(): Int {
    val fontScale = mAppContext.resources.displayMetrics.scaledDensity
    return (this * fontScale + 0.5f).toInt()
}
