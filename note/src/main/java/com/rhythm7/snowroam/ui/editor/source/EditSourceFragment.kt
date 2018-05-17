package com.rhythm7.snowroam.ui.editor.source

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.rhythm7.core.base.fragment.BaseDaggerFragment
import com.rhythm7.core.base.mvvm.status.Status
import com.rhythm7.core.di.module.ViewModelFactory
import com.rhythm7.mid.args.NOTE_ID
import com.rhythm7.mid.router.FRAG_EDIT_SOURECE
import com.rhythm7.snowroam.R
import com.rhythm7.snowroam.ui.editor.EditorViewModel
import kotlinx.android.synthetic.main.fragment_editor.*
import javax.inject.Inject

/**
 * 编辑内容
 * Created by Jaminchanks on 2018-05-06.
 */

@Route(path = FRAG_EDIT_SOURECE)
class EditSourceFragment: BaseDaggerFragment(true){

    @Inject
    lateinit var mViewModelFactory: ViewModelFactory
    lateinit var mEditorViewModel: EditorViewModel

    @JvmField  @Autowired(name = NOTE_ID)
    var mNoteId: Long? = 0L

    override fun initViewAndEvent() {
        mEditorViewModel = ViewModelProviders.of(activity!!, mViewModelFactory).get(EditorViewModel::class.java)
        mEditorViewModel.setNoteId(mNoteId)
        mEditorViewModel.loadNoteDetail()

        mEditorViewModel.noteDetail.observe(activity!!, Observer {
            _et_content.setText(it?.content)
            _et_content.setSelection(it?.content?.length ?: 0)
        })
    }

    override fun getLayoutId(): Int = R.layout.fragment_editor

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_editor, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_undo -> {
                _et_content.undo()
                return true
            }
            R.id.action_redo -> {
                _et_content.redo()
                return true
            }
            R.id.action_save -> {
                val content = _et_content.text.toString()
                if (content.isBlank()) {
                    showToast("内容不能为空")
                    return true
                }

                /// TODO: 2018-05-18 抽离重复代码
                mEditorViewModel.saveNoteContent(content).observe(activity!!, Observer {
                    when(it?.status ) {
                        Status.SUCCESS -> showToast("保存成功")
                        Status.ERROR -> showToast(it.message!!)
                        Status.LOADING -> {}
                    }
                })
                return true
            }
            R.id.action_delete -> {
                mEditorViewModel.deleteNote().observe(activity!!, Observer {
                    when(it?.status ) {
                        Status.SUCCESS -> activity!!.finish()
                        Status.ERROR -> showToast(it.message!!)
                        Status.LOADING -> {}
                    }
                })
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}