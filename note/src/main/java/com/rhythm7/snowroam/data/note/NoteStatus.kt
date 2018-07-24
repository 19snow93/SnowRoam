package com.rhythm7.snowroam.data.note

import com.rhythm7.core.utlis.ext.resIdToColor
import com.rhythm7.snowroam.R

/**
 * 不同的事件记录用不同的颜色区分开
 * Created by Jaminchanks on 2018-03-29.
 */

enum class NoteStatus(var code: Int) {
    DOING(1),
    FINISH(2),
    STAGE(3),
    GIVE_UP(4);

    val color: Int = when (code) {
        1   -> R.color.colorPrimary.resIdToColor()
        2   -> R.color.orange_600.resIdToColor()
        3   -> R.color.grey_600.resIdToColor()
        4   -> R.color.red_600.resIdToColor()
        else-> R.color.white_1000.resIdToColor()
    }

    val desc: String = when (code) {
        1   ->  "进行中"
        2   ->  "已完成"
        3   ->  "暂时搁置"
        4   ->  "已放弃"
        else -> ""
    }
}