package com.rhythm7.snowroam.ui.editor

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.RoomDatabase
import com.rhythm7.core.base.mvvm.BaseViewModel
import com.rhythm7.core.base.mvvm.status.Resource
import com.rhythm7.core.utlis.rx.io_main
import com.rhythm7.snowroam.data.NoteDatabase
import com.rhythm7.snowroam.data.note.Note
import com.rhythm7.snowroam.data.note.NoteDao
import com.rhythm7.snowroam.data.note.NoteStatus
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import java.sql.Date
import javax.inject.Inject

/**
 * Created by Jaminchanks on 2018-05-18.
 */
class EditorViewModel
@Inject constructor(private val noteDao: NoteDao, private val db: NoteDatabase): BaseViewModel() {

    private val _noteDetail = MutableLiveData<Note>()

    private val _noteId = MutableLiveData<Long?>()

    val noteDetail: LiveData<Note>
        get() = _noteDetail

    fun setNoteId(id: Long?) {
        _noteId.value = id
    }

    /**
     * 加载笔记详情
     */
    fun loadNoteDetail() {
        if (_noteId.value == null) return

        noteDao.getNoteById(_noteId.value!!)
                .io_main()
                .subscribe({
                    _noteDetail.value = it
                })
                .addToDisposable()
    }


    /**
     * 保存笔记
     */
    fun saveNoteContent(content: String?):  LiveData<Resource<Void>>  {
        val result = MutableLiveData<Resource<Void>>()
        result.value = Resource.loading(null)

        if (content.isNullOrBlank()) {
            result.value =  Resource.error("内容不可为空", null)
            return result
        }

        val currentTime = System.currentTimeMillis()
        val note = Note()
        note.id = _noteId.value
        note.content = content
        note.lastUpdateTime = Date(currentTime)

        /// TODO: 2018-05-18 现在未加状态筛选
        note.status = NoteStatus.DOING.code

        // 标题截取内容的第一行，简要截取第二行，若第二行为空则使用第一行的内容
        note.title = content!!.trimStart().substringBefore("\n")
        note.brief = content.trimStart ().substringAfter("\n").substringBefore("\n").trimStart()

        if (note.brief?.isBlank() == true) {
            note.brief = note.title
        }

        if (note.id == null) {
            note.createTime = Date(currentTime)
        }

        Completable.fromAction({
            noteDao.insertOrSaveNote(note)
        })
                .io_main()
                .subscribe({
                    _noteDetail.value = note
                    result.value = Resource.success(null)
                }, {
                    result.value = Resource.error("${it.message}", null)
                })
                .addToDisposable()

        return result
    }


    fun deleteNote(): LiveData<Resource<Void>> {
        val result = MutableLiveData<Resource<Void>>()
        result.value = Resource.loading(null)

        if (_noteId.value == null) {
            result.value =  Resource.error("笔记内容为空", null)
            return result
        }

        Completable.fromAction{
            noteDao.deleteById(_noteId.value!!)
        }.io_main()
                .subscribe({
                    result.value = Resource.success(null)
                }, {
                    result.value = Resource.error("${it.message}", null)
                })

        return result
    }
}