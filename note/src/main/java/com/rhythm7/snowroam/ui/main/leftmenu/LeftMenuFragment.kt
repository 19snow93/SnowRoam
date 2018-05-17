package com.rhythm7.snowroam.ui.main.leftmenu

import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.support.v7.app.AppCompatDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rhythm7.core.base.fragment.BaseDaggerFragment
import com.rhythm7.core.di.module.ViewModelFactory
import com.rhythm7.core.utlis.delegates.AutoDisposable
import com.rhythm7.core.utlis.ext.resIdToString
import com.rhythm7.snowroam.R
import com.rhythm7.snowroam.data.category.Category
import com.rhythm7.snowroam.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_main_left_menu.*
import javax.inject.Inject

/**
 * 左侧菜单栏
 * Created by Jaminchanks on 2018-05-09.
 */

class LeftMenuFragment : BaseDaggerFragment() {

    @Inject
    lateinit var mViewModelFactory: ViewModelFactory

    private var mMainViewModel: MainViewModel by AutoDisposable()

    private lateinit var mAdapter: BaseQuickAdapter<Category, BaseViewHolder>

    override fun initViewAndEvent() {
        initRecycleView()
        initEvent()
    }

    override fun getLayoutId(): Int = R.layout.fragment_main_left_menu


    private fun initRecycleView() {
        mAdapter = object : BaseQuickAdapter<Category, BaseViewHolder>(R.layout.item_category_menu) {
            override fun convert(helper: BaseViewHolder?, item: Category?) {
                helper!!.getView<TextView>(R.id._tv_category).text = item?.name
            }
        }

        mAdapter.setOnItemClickListener { adapter, _, position ->
            mMainViewModel.setCategoryId((adapter.getItem(position) as Category).id)
        }

        _rcv_main_menu.layoutManager = LinearLayoutManager(activity)
        _rcv_main_menu.adapter = mAdapter

        _tv_add_category.setOnClickListener{
            showAddCategoryDialog()
        }
    }

    private fun initEvent() {
        mMainViewModel = ViewModelProviders.of(activity!!, mViewModelFactory).get(MainViewModel::class.java)

        mMainViewModel.categoryList.observe(activity!!, Observer {
            mAdapter.setNewData(it)
        })

    }


    private fun showAddCategoryDialog() {
        val inputView = LayoutInflater.from(activity).inflate(R.layout.dialog_with_input, null)
        val inputField = inputView.findViewById<EditText>(R.id._et_input_field)
        inputView.findViewById<TextView>(R.id._tv_input_title).text = R.string.tx_category_add.resIdToString()
        val dialog = AlertDialog.Builder(activity)
                .setView(inputView)
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, which ->
                    run {
                        val content = inputField.text
                        if (content.isNotBlank()) {
                            mMainViewModel.addCategory(content.toString())
                            dialog.dismiss()
                        }
                    }
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }.create()


        dialog.show()

    }
}