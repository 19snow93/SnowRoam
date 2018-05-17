package com.rhythm7.core.widget.rcv

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.rhythm7.core.R

/**
 * Created by Jaminchanks on 2018-03-30.
 */
class LinearDividerItemDecoration : DividerItemDecoration {
    private lateinit var mContext: Context
    private var mDividerSize = 1
    private var mOrientation: Int = VERTICAL
    private var mIsDrawBefore = false
    private var mIsDrawAfter = false

    constructor(context: Context, orientation: Int, dividerSize: Int = 1,
                isDrawBefore: Boolean = false, isDrawAfter: Boolean = false) : super(context,  orientation) {
        this.mContext = context
        this.mOrientation = orientation
        this.mDividerSize = dividerSize
        this.mIsDrawBefore = isDrawBefore
        this.mIsDrawAfter = isDrawAfter

        setDefaultDrawable()
    }


    private fun setDefaultDrawable() {
        setDrawable(ColorDrawable(ContextCompat.getColor(mContext, R.color.grey_300)))
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        val layoutManager = parent?.layoutManager as LinearLayoutManager
        val itemCount = layoutManager.itemCount

        //最后一个item的偏移
        if (!mIsDrawAfter && parent.getChildAdapterPosition(view) == itemCount - 1) {
            outRect?.set(0, 0,0,0)
        } else if (mIsDrawBefore && parent.getChildAdapterPosition(view) == 0) {         //第一个item的偏移
            if (mOrientation == VERTICAL) {
                outRect?.set(0, mDividerSize, 0, mDividerSize)
            } else {
                outRect?.set(mDividerSize, 0, mDividerSize, 0)
            }

        } else {
            if (mOrientation == VERTICAL) {
                outRect?.set(0, 0,  0, mDividerSize)
            } else {
                outRect?.set(0, 0, mDividerSize, 0)
            }
        }

    }

}