package com.rhythm7.core.utlis.ext

import java.text.DecimalFormat

/**
 * Created by Jaminchanks on 2018/3/29.
 */

/**
 * 转换文件大小
 * @return
 */

fun Long.toMenoryFormat(): String {
    val units = arrayOf("KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB", "BB", "NB", "DB", "CB")
    val numberFormat = DecimalFormat("###")

    var n = toDouble()
    var i = -1
    while (n >= 999.5) {
        n /= 1024
        ++i
    }
    return if (i < 0) "$this Byte"
    else "${numberFormat.format(n)} ${units[i]}"
}


/**
 * 返回最长长度为maxNum的列表
 */
public fun <T> List<T>.atMost(maxNum: Int): List<T>? {
    return if (this.size > maxNum) {
        this.subList(0, maxNum)
    } else {
        this
    }
}