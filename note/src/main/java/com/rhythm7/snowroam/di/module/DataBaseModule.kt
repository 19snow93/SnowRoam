package com.rhythm7.snowroam.di.module

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.rhythm7.core.utlis.Logger
import com.rhythm7.core.utlis.mAppContext
import com.rhythm7.snowroam.data.NoteDatabase
import com.rhythm7.snowroam.data.category.CategoryDao
import com.rhythm7.snowroam.data.note.NoteDao
import dagger.Module
import dagger.Provides

/**
 * Created by Jaminchanks on 2018-05-07.
 */

@Module
class DataBaseModule {
    @Provides
    fun provideDatabase(): NoteDatabase {
        return Room.databaseBuilder(mAppContext, NoteDatabase::class.java, "snowRoam")
                .addCallback(object : RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val insertCategorySQL = "INSERT INTO category VALUES (1, '未分类')"
                        db.execSQL(insertCategorySQL)
                        Logger.i("插入默认数据")
                    }
                })
                .build()
    }

    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase) : NoteDao {
        return noteDatabase.getNoteDao()
    }

    @Provides
    fun provideCategoryDao(noteDatabase: NoteDatabase): CategoryDao {
        return noteDatabase.getCategoryDao()
    }

}