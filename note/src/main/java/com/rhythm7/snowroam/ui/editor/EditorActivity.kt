package com.rhythm7.snowroam.ui.editor

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.rhythm7.core.base.activity.BaseActivity
import com.rhythm7.core.utlis.ext.navigateToFragment
import com.rhythm7.mid.args.NOTE_ID
import com.rhythm7.mid.router.ACT_EDIT_SOURCE
import com.rhythm7.mid.router.FRAG_EDIT_SOURCE
import com.rhythm7.snowroam.R
import com.rhythm7.snowroam.ui.editor.source.EditSourceFragment
import kotlinx.android.synthetic.main.activity_editor.*

@Route(path = ACT_EDIT_SOURCE)
class EditorActivity : BaseActivity() {
    @JvmField  @Autowired(name = NOTE_ID)
    var mNoteId: Long = 0L

    private lateinit var mEditSourceFragment : EditSourceFragment

    override fun initViewAndEvent() {

        setToolbar("")

        mEditSourceFragment = navigateToFragment(FRAG_EDIT_SOURCE){
            it.withLong(NOTE_ID, mNoteId)
        }
        _vp_editor.adapter = getFragmentAdapter()
    }

    override fun getLayoutId(): Int = R.layout.activity_editor


    private fun getFragmentAdapter() = object : FragmentPagerAdapter(supportFragmentManager) {
        override fun getItem(position: Int): Fragment {
            return mEditSourceFragment
        }
        override fun getCount(): Int = 1
    }


}