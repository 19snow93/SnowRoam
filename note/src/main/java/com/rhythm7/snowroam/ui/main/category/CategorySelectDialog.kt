package com.rhythm7.snowroam.ui.main.category

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rhythm7.core.utlis.ScreenUtil
import com.rhythm7.core.utlis.ext.dp2px
import com.rhythm7.snowroam.R
import com.rhythm7.snowroam.data.category.Category
import kotlinx.android.synthetic.main.dialog_category_select.*

/**
 * 选择分类弹窗
 * Created by Jaminchanks on 2018-05-17.
 */
class CategorySelectDialog(context: Context, private val list: List<Category>?)
    : AlertDialog(context) {
    private var mItemSelectListener: (categoryId: Long)-> Unit = {}

    private lateinit var mAdapter: CategorySelectorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = View.inflate(context, R.layout.dialog_category_select, null)
        setContentView(view)

        configView()
    }


    private fun configView() {
        mAdapter = CategorySelectorAdapter()
        _rcv_category.adapter = mAdapter
        mAdapter.setNewData(list)

        _rcv_category.layoutManager = LinearLayoutManager(context)
        _rcv_category.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        mAdapter.setOnItemClickListener { adapter, _, position ->
            mItemSelectListener((adapter.getItem(position) as Category).id!!)
        }
    }


    /**
     * 设置子项监听
     */
    fun setOnItemSelectedListener(listener: (categoryId: Long)-> Unit){
        this.mItemSelectListener = listener
    }

    /**
     * 分类Adapter
     */
    companion object {
        class CategorySelectorAdapter : BaseQuickAdapter<Category, BaseViewHolder>(R.layout.item_category_select) {
            override fun convert(helper: BaseViewHolder?, item: Category?) {
                helper!!.getView<TextView>(R.id._tv_category).text = item?.name
            }
        }
    }
}