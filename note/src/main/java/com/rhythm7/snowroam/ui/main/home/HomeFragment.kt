package com.rhythm7.snowroam.ui.main.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.rhythm7.core.base.fragment.BaseDaggerFragment
import com.rhythm7.core.di.module.ViewModelFactory
import com.rhythm7.core.utlis.delegates.AutoDisposable
import com.rhythm7.core.widget.rcv.LinearDividerItemDecoration
import com.rhythm7.mid.router.FRAG_MAIN
import com.rhythm7.snowroam.R
import com.rhythm7.snowroam.adapter.NoteItemAdapter
import com.rhythm7.snowroam.data.note.Note
import com.rhythm7.snowroam.ui.main.MainViewModel
import com.rhythm7.snowroam.ui.main.category.CategorySelectDialog
import kotlinx.android.synthetic.main.fragment_main_child.*
import javax.inject.Inject

/**
 * 主页显示笔记的fragment
 * Created by Jaminchanks on 2018-03-30.
 */
@Route(path = FRAG_MAIN)
class HomeFragment : BaseDaggerFragment() {
    @Inject
    lateinit var mViewModelFactory: ViewModelFactory

    private var mMainViewModel: MainViewModel by AutoDisposable()

    private lateinit var mAdapter: NoteItemAdapter

    override fun initViewAndEvent() {
        initView()
        initEvent()
    }

    override fun getLayoutId(): Int = R.layout.fragment_main_child


    private fun initView() {
        _rcv_main.layoutManager = LinearLayoutManager(activity)
        _rcv_main.addItemDecoration(LinearDividerItemDecoration(activity!!, LinearLayoutManager.VERTICAL, 12, true, false))
        mAdapter = NoteItemAdapter()
        mAdapter.bindToRecyclerView(_rcv_main)

        mAdapter.setOnItemOptionSeletedListener(getNoteAdapterListener())

        _rcv_main.setOnTouchListener { _, _ ->
            mAdapter.hideAllOptionView()
            false
        }
    }

    private fun initEvent() {
        mMainViewModel = ViewModelProviders.of(activity!!, mViewModelFactory).get(MainViewModel::class.java)

        mMainViewModel.categoryId.observe(activity!!, Observer {
            mMainViewModel.loadCategoryList()
        })

        mMainViewModel.categoryList.observe(activity!!, Observer {
            mMainViewModel.loadNoteList()
        })

        mMainViewModel.noteList.observe(activity!!, Observer {
            //fixme 2018.5.17 太过粗暴
            mAdapter.setNewData(it)
        })

    }


    /**
     * noteAdapter 监听器
     */
    private fun getNoteAdapterListener() = object : NoteItemAdapter.OnItemOptionSelectedListener {
        override fun onChangeCategory(position: Int, note: Note) {
            val dialog = CategorySelectDialog(activity!!, mMainViewModel.categoryList.value)
            dialog.setOnItemSelectedListener {
                note.categoryId = it
                mMainViewModel.updateNote(position, note).observe(activity!!, Observer {
                    dialog.dismiss()
                })
            }

            dialog.show()

        }

        override fun onChangeStatus(position: Int, note: Note) {

        }

        override fun onDelete(position: Int) {
        }
    }
}