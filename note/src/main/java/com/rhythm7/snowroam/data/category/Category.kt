package com.rhythm7.snowroam.data.category

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import io.reactivex.annotations.NonNull

/**
 * 一个归类下有很多个笔记
 * Created by Jaminchanks on 2018-05-08.
 */

@Entity(tableName = "category")
class Category {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    var name: String? = null

}