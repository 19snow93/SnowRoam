package com.rhythm7.core.utlis

import java.util.*

/**
 * Created by Jaminchanks on 2018-03-29.
 */

fun Date.toDateString(): String {
    return TimeUtil.getSimpleUserCanViewTime(this.time)
}