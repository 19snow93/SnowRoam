package com.rhythm7.core.utlis

import android.graphics.Color

/**
 * Created by Jaminchanks on 2018-03-27.
 */
/**
 * 获取过渡颜色
 * 根据fraction值来计算当前的颜色
 */
public fun getTransitionColor(fraction: Float, startColor: Int, endColor: Int): Int {
    val redCurrent: Int
    val blueCurrent: Int
    val greenCurrent: Int
    val alphaCurrent: Int

    val redStart = Color.red(startColor)
    val blueStart = Color.blue(startColor)
    val greenStart = Color.green(startColor)
    val alphaStart = Color.alpha(startColor)

    val redEnd = Color.red(endColor)
    val blueEnd = Color.blue(endColor)
    val greenEnd = Color.green(endColor)
    val alphaEnd = Color.alpha(endColor)

    val redDifference = redEnd - redStart
    val blueDifference = blueEnd - blueStart
    val greenDifference = greenEnd - greenStart
    val alphaDifference = alphaEnd - alphaStart

    redCurrent = (redStart + fraction * redDifference).toInt()
    blueCurrent = (blueStart + fraction * blueDifference).toInt()
    greenCurrent = (greenStart + fraction * greenDifference).toInt()
    alphaCurrent = (alphaStart + fraction * alphaDifference).toInt()

    return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent)
}