package com.rhythm7.snowroam.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.rhythm7.snowroam.data.category.Category
import com.rhythm7.snowroam.data.category.CategoryDao
import com.rhythm7.snowroam.data.converter.Converters
import com.rhythm7.snowroam.data.note.Note
import com.rhythm7.snowroam.data.note.NoteDao

/**
 * Created by Jaminchanks on 2018-05-07.
 */

@Database(entities = [Note::class, Category::class], version = 1)
@TypeConverters(value = [Converters::class])
abstract class NoteDatabase : RoomDatabase(){
    abstract fun getNoteDao(): NoteDao

    abstract fun getCategoryDao(): CategoryDao
}