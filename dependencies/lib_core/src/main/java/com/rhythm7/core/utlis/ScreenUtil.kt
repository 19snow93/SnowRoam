package com.rhythm7.core.utlis

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * 屏幕设置相关类
 * Created by chenmingzhen on 16-6-6.
 */
object ScreenUtil {
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    private var statusBarHeight: Int = 0 //状态栏高度

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    fun setBackgroundAlpha(activity: Activity, bgAlpha: Float) {
        val lp = activity.window.attributes
        lp.alpha = bgAlpha //0.0-1.0
        activity.window.attributes = lp
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth(context: Context): Int {
        if (screenWidth <= 0) {
            val display = (context
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                    .defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)
            screenWidth = outMetrics.widthPixels
        }
        return screenWidth
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(context: Context): Int {
        if (screenHeight <= 0) {
            val display = (context
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                    .defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)
            screenHeight = outMetrics.heightPixels
        }
        return screenHeight
    }

    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(act: Activity): Int {
        if (statusBarHeight <= 0) {
            val window = act.window.findViewById<View>(
                    Window.ID_ANDROID_CONTENT)
            val rect = Rect()
            window.getWindowVisibleDisplayFrame(rect)
            statusBarHeight = rect.top
        }
        return statusBarHeight
    }

}
