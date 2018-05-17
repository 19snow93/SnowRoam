package com.rhythm7.snowroam.adapter

import android.animation.ObjectAnimator
import android.app.Activity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rhythm7.mid.router.ACT_EDIT_SOURCE
import com.rhythm7.core.utlis.Logger
import com.rhythm7.core.utlis.ext.navigateToActivity
import com.rhythm7.core.utlis.toDateString
import com.rhythm7.mid.args.NOTE_ID
import com.rhythm7.mid.router.PATH_CATEGORY_SELECT
import com.rhythm7.snowroam.R
import com.rhythm7.snowroam.data.note.Note

/**
 * Created by Jaminchanks on 2018-03-28.
 */
class NoteItemAdapter : BaseQuickAdapter<Note, BaseViewHolder>(R.layout.item_note_brief) {

    private var mLastOpenItem: Note? = null

    private var mOnItemOptionsListener: OnItemOptionSelectedListener? = null

    override fun convert(helper: BaseViewHolder, item: Note?) {
        helper.getView<TextView>(R.id._tv_item_note_title).text = item?.title ?: ""
        helper.getView<TextView>(R.id._tv_item_note_brief).text = item?.content ?: ""
        helper.getView<TextView>(R.id._tv_item_note_time).text = item?.lastUpdateTime?.toDateString() ?: ""

        setOnclickListener(helper, item)
    }

    private fun setOnclickListener(helper: BaseViewHolder, item: Note?) {
        val noteView = helper.getView<View>(R.id._cstly_note_item)

        noteView.setOnClickListener {
            hideAllOptionView()
            navigateToActivity(ACT_EDIT_SOURCE) {
                it.withLong(NOTE_ID, item?.id ?: 0L)
            }
        }

        noteView.setOnLongClickListener {
            hideAllOptionView()

            val animator =  ObjectAnimator.ofFloat(noteView, "translationX", 0f, noteView.width.toFloat())
            animator.duration = 200
            animator.interpolator = AccelerateInterpolator()
            animator.start()

            mLastOpenItem = item
            true
        }

        helper.getView<ImageView>(R.id._ibtn_label).setOnClickListener {
            hideAllOptionView()
            mOnItemOptionsListener?.onChangeCategory(data.indexOf(item), item!!)
        }

        helper.getView<ImageView>(R.id._ibtn_done).setOnClickListener {
            hideAllOptionView()
            mOnItemOptionsListener?.onChangeStatus(data.indexOf(item), item!!)
        }

        helper.getView<ImageView>(R.id._ibtn_delete).setOnClickListener {
            hideAllOptionView()
            mOnItemOptionsListener?.onDelete(data.indexOf(item))
        }
    }


    /**
     * 关闭所有item的展开状态
     */
    public fun hideAllOptionView() {
        if (mLastOpenItem != null) {
            val noteView = getViewByPosition(data.indexOf(mLastOpenItem!!), R.id._cstly_note_item)!!
            val animator =  ObjectAnimator.ofFloat(noteView, "translationX", noteView.width.toFloat(), 0f)
            animator.duration = 200
            animator.interpolator = AccelerateInterpolator()
            animator.start()
            mLastOpenItem = null
        }
    }


    fun setOnItemOptionSeletedListener(listener: OnItemOptionSelectedListener) {
        this.mOnItemOptionsListener = listener
    }

    /**
     * item选项监听
     */
    interface OnItemOptionSelectedListener {
        fun onChangeCategory(position: Int, note: Note)

        fun onChangeStatus(position: Int, note: Note)

        fun onDelete(position: Int)
    }
}

