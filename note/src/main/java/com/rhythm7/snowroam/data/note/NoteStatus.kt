package com.rhythm7.snowroam.data.note

import com.rhythm7.core.utlis.ext.resIdToColor
import com.rhythm7.snowroam.R

/**
 * 不同的事件记录用不同的颜色区分开
 * Created by Jaminchanks on 2018-03-29.
 */

enum class NoteStatus(var code: Int) {
    DOING(0),
    FINISH(1),
    STAGE(2),
    GIVE_UP(3);

    val color: Int = when (code) {
        0   -> R.color.colorPrimary.resIdToColor()
        1   -> R.color.orange_600.resIdToColor()
        2   -> R.color.grey_600.resIdToColor()
        3   -> R.color.red_600.resIdToColor()
        else-> R.color.white_1000.resIdToColor()
    }

    val desc: String = when (code) {
        0   ->  "进行中"
        1   ->  "已完成"
        2   ->  "暂时搁置"
        3   ->  "已放弃"
        else -> ""
    }
}