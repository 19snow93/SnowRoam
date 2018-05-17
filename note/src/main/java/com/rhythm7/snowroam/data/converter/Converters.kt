package com.rhythm7.snowroam.data.converter

import android.arch.persistence.room.TypeConverter
import com.rhythm7.snowroam.data.note.NoteStatus
import java.sql.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: java.sql.Date?): Long? {
        return date?.time
    }

}