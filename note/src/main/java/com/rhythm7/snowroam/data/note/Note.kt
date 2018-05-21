package com.rhythm7.snowroam.data.note

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.rhythm7.snowroam.data.category.Category
import io.reactivex.annotations.NonNull
import java.sql.Date

/**
 * 笔记
 * Created by Jaminchanks on 2018-03-28.
 */
@Entity(tableName = "note",
        foreignKeys = [(ForeignKey(entity = Category::class,
                parentColumns = ["id"],
                childColumns = ["categoryId"],
                onDelete = ForeignKey.CASCADE
                ))]
)
class Note {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null //笔记id

    var title: String? = null //笔记标题

    var brief: String? = null //简要

    var content: String? = null //笔记内容

    var createTime: Date? = Date(System.currentTimeMillis()) //创建时间

    var lastUpdateTime: Date? = Date(System.currentTimeMillis()) //修改时间

    var status: Int? = 1 //状态

    var categoryId: Long? = 1 //分类
}

