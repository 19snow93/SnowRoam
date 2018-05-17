package com.rhythm7.core.utlis.ext

import android.app.*
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.IdRes
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.rhythm7.core.R
import java.lang.ref.WeakReference
import java.util.*
import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

/**
 * Created by Jaminchanks on 2018-03-14.
 */

private val mActivityList by lazy { LinkedList<Activity>() }

/**
 * 添加fragment
 */
fun AppCompatActivity.addFragment(fragment: Fragment, @IdRes frameIdRes: Int) {
    doOnTransaction(this.supportFragmentManager) {
        it.add(frameIdRes, fragment)
    }
}

/**
 * 显示fragment
 */
fun AppCompatActivity.showFragment(fragment: Fragment) {
    doOnTransaction(this.supportFragmentManager) {
        it.show(fragment)
    }
}

/**
 * 隐藏fragment
 */
fun AppCompatActivity.hideFragment(
                          fragment: Fragment) {
    doOnTransaction(this.supportFragmentManager) {
        it.hide(fragment)
    }
}


private fun doOnTransaction(fragmentManager: FragmentManager,
                            action: (transaction: FragmentTransaction)-> Unit) {
    val transaction = fragmentManager.beginTransaction()
    action(transaction)
    transaction.commit()
}



fun Activity.pushToStack() {
    mActivityList.add(this)
}

fun Activity.popFromStack() {
    mActivityList.remove(this)
}

/**
 * 退出所有
 */
fun popAllActivity() {
    mActivityList.forEach {
        it.finish()
    }
    mActivityList.clear()
}



/**
 * 隐藏输入法键盘
 */
fun Activity.hideInput() {
    val weakReference = WeakReference(this)
    weakReference.hideInput()
}

/**
 * 隐藏键盘
 */
fun WeakReference<Activity>.hideInput() {
    val imm = this.get()!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isActive && this.get()!!.currentFocus != null) {
        imm.hideSoftInputFromWindow(this.get()!!.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}


/**
 * 显示输入法键盘
 */
fun WeakReference<Activity>.showInput(editText: EditText) {
    editText.isFocusable = true
    editText.requestFocus()
    val imm = this.get()!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
}

/**
 * 改变Activity的透明度
 */
fun Activity.changeBackGroupAlpha(alpha: Float) {
    val params = this.window.attributes//设置背景变暗
    params.alpha = alpha
    this.window.attributes = params
}


/**
 * 设置透明状态栏
 */
fun Activity.setTranslucentWindows() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
        val decorView = this.window.decorView
        val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.systemUiVisibility = option
        this.window.statusBarColor = Color.TRANSPARENT
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
        val localLayoutParams = this.window.attributes
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
    }
}

/**
 * 19API以上 读取到状态栏高度才有意义
 *
 * @param context
 * @return
 */
fun Activity.getStatusBarHeight(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        val resourceId = this.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) this.resources.getDimensionPixelSize(resourceId) else 0
    } else {
        0
    }
}

/**
 * 设置状态栏颜色为App主色
 * 配合[.setTranslucentWindows]方法使用
 * 主要方法为添加一个View并设置背景色添加到系统contentView中
 *
 * @param activity
 */
fun  Activity.addStatusBarBackground() {

    //当API21+：能够调用系统API直接对状态栏着色
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        this.window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
    }

    val height: Int = getStatusBarHeight()
    if (height <= 0) {
        return
    }

    val layout = this.findViewById<View>(android.R.id.content) as FrameLayout
    val statusLayout = FrameLayout(this)
    statusLayout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)

    val typedValue = TypedValue()
    val a = this.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorPrimary))
    val color = a.getColor(0, 0)
    a.recycle()
    statusLayout.setBackgroundColor(color)
    layout.addView(statusLayout)
}


/**
 * 设置状态栏黑色字体图标，
 * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
 * @param activity
 * @return 1:MIUUI 2:Flyme 3:android6.0
 */
fun Activity.setStatusBarLightMode(): Int {
    var result = 0
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        when {
            setMIUIStatusBarLightMode(this.window, true) -> result = 1
            setFlymeStatusBarLightMode(this.window, true) -> result = 2
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                result = 3
            }
        }
    }
    return result
}

/**
 * 已知系统类型时，设置状态栏黑色字体图标。
 * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
 * @param activity
 * @param type 1:MIUUI 2:Flyme 3:android6.0
 */
fun Activity.setStatusBarLightMode(type: Int) {
    when (type) {
        1 -> setMIUIStatusBarLightMode(this.window, true)
        2 -> setFlymeStatusBarLightMode(this.window, true)
        3 -> this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

}

/**
 * 清除MIUI或flyme或6.0以上版本状态栏黑色字体
 */
fun setStatusBarDarkMode(activity: Activity, type: Int) {
    when (type) {
        1 -> setMIUIStatusBarLightMode(activity.window, false)
        2 -> setFlymeStatusBarLightMode(activity.window, false)
        3 -> activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }

}


/**
 * 设置状态栏图标为深色和魅族特定的文字风格
 * 可以用来判断是否为Flyme用户
 * @param window 需要设置的窗口
 * @param dark 是否把状态栏字体及图标颜色设置为深色
 * @return  boolean 成功执行返回true
 */
fun setFlymeStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
    var result = false
    if (window != null) {
        try {
            val lp = window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java
                    .getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            if (dark) {
                value = value or bit
            } else {
                value = value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            window.attributes = lp
            result = true
        } catch (e: Exception) {

        }

    }
    return result
}

/**
 * 设置状态栏字体图标为深色，需要MIUIV6以上
 * @param window 需要设置的窗口
 * @param dark 是否把状态栏字体及图标颜色设置为深色
 * @return  boolean 成功执行返回true
 */
fun setMIUIStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
    var result = false
    if (window != null) {
        val clazz = window.javaClass
        try {
            var darkModeFlag = 0
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            if (dark) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
            }
            result = true
        } catch (e: Exception) {

        }

    }
    return result
}
