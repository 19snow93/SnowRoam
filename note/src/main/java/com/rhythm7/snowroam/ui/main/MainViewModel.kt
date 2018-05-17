package com.rhythm7.snowroam.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.rhythm7.core.base.mvvm.BaseViewModel
import com.rhythm7.core.utlis.rx.io_main
import com.rhythm7.snowroam.data.DEFAULT_PAGE_SIZE
import com.rhythm7.snowroam.data.category.Category
import com.rhythm7.snowroam.data.category.CategoryDao
import com.rhythm7.snowroam.data.note.Note
import com.rhythm7.snowroam.data.note.NoteDao
import io.reactivex.Completable
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import javax.inject.Inject

/**
 * Created by Jaminchanks on 2018-05-17.
 */

class MainViewModel
@Inject constructor(private val noteDao: NoteDao, private val categoryDao: CategoryDao) : BaseViewModel() {

    private val _categoryList = MutableLiveData<List<Category>>() //分类列表

    private val _noteList = MutableLiveData<List<Note>>() //文章列表

    private var _categoryId = MutableLiveData<Long?>() //指定的分类id

    private var _notePageSize = MutableLiveData<Int>() //每次查找笔记的第几页页数

    ///********* 可供外部使用 *********///
    val categoryList: LiveData<List<Category>>
        get() = _categoryList

    val noteList: LiveData<List<Note>>
        get() = _noteList

    val categoryId: LiveData<Long?>
        get() = _categoryId


    /**
     * 设置所选的分类
     */
    fun setCategoryId(categoryId: Long?) {
        _categoryId.value = categoryId
    }


    /**
     * 当前加载页数
     */
    fun setNotePageSize(pageSize: Int) {
        _notePageSize.value = pageSize
    }

    /**
     * 获取所有分类
     */
    fun loadCategoryList() {
        categoryDao.getAllCategory()
                .io_main()
                .subscribe{
                    _categoryList.value = it
                }
                .addToDisposable()
    }

    /**
     * 添加分类
     */
    fun addCategory(name: String) {
        Completable.fromAction{
            val category = Category()
            category.name = name
            categoryDao.insertCategory(category)
        }
                .andThen(Publisher<List<Category>> { s ->
                    val newList = categoryDao.getAllCategoryBlock()
                    s.onNext(newList)
                })
                .io_main()
                .subscribe{
                    _categoryList.value = it
                }
                .addToDisposable()
    }



    /**
     * 根据分类获取笔记
     */
    fun loadNoteList(){
        val offset = (_notePageSize.value ?: 0).times(DEFAULT_PAGE_SIZE)
        val result = if (_categoryId.value == null)
            noteDao.pageData(offset)
        else
            noteDao.pageDataByCategory(_categoryId.value!!, offset)

        result.io_main()
                .subscribe{
                    _noteList.value = it
                }
                .addToDisposable()
    }


    /**
     * 删除笔记
     */
    fun deleteNote(note: Note) {
        Completable.fromAction{
            noteDao.delete(note)
        }
                .io_main()
                .subscribe{
                    (_noteList.value as ArrayList<Note>).remove(note)
                }
                .addToDisposable()
    }


    /**
     * 更新笔记
     */
    fun updateNote(position: Int, note: Note): LiveData<Byte> {
        val result = MutableLiveData<Byte>()
        Completable.fromAction{
            noteDao.update(note)
        }
                .io_main()
                .subscribe{
                    (_noteList.value as ArrayList<Note>)[position] = note
                    result.value = 1
                }
                .addToDisposable()

        return result
    }

}