package com.rhythm7.snowroam.data.note

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.rhythm7.snowroam.data.category.Category
import io.reactivex.Flowable
import org.intellij.lang.annotations.Flow

/**
 * Created by Jaminchanks on 2018-05-07.
 */

@Dao
abstract class NoteDao {
    private val DEFAULT_PAGE_SIZE: Int
        get() = 25

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg note: Note)

    @Update
    abstract fun update(note: Note)

    @Delete
    abstract fun delete(note: Note)

    @Query("DELETE FROM Note WHERE id = :id")
    abstract fun deleteById(id: Long)
    
    @Query("SELECT * FROM Note WHERE id = :id")
    abstract fun getNoteById(id: Long): Flowable<Note>

    @Query("SELECT * FROM Note WHERE id = :id")
    abstract fun getNoteByIdBlock(id: Long): Note

    @Query("SELECT max(id) FROM Note")
    abstract fun getMaxId(): Long?

    @Query("SELECT * FROM Note WHERE title = :title ORDER BY lastUpdateTime DESC LIMIT :offset, :limit")
    abstract fun getNoteByTitle(title: String, offset: Int = 0, limit: Int = DEFAULT_PAGE_SIZE) :Flowable<List<Note>>

    /// TODO: 2018-05-14 不要把note的所有字段都查询出来
    @Query("SELECT * FROM Note ORDER BY lastUpdateTime DESC LIMIT :offset, :limit")
    abstract fun pageData(offset: Int = 0, limit: Int = DEFAULT_PAGE_SIZE): Flowable<List<Note>>

    /// TODO: 2018-05-14 不要把note的所有字段都查询出来
    @Query("SELECT * FROM Note WHERE categoryId = :categoryId ORDER BY lastUpdateTime DESC LIMIT :offset, :limit ")
    abstract fun pageDataByCategory(categoryId: Long, offset: Int = 0, limit: Int = DEFAULT_PAGE_SIZE): Flowable<List<Note>>


    /**
     * 新增笔记并返回该笔记id
     */
    @Transaction
    public open fun insertOrSaveNote(note: Note): Note {
        if(note.id == null) {
            val maxId = getMaxId()
            note.id = maxId
        }
        update(note)

        return note
    }
}