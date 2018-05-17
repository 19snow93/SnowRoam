package com.rhythm7.snowroam.widget.edit

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import java.util.*

/**
 * 支持撤销和反撤销的EditText
 * Created by Jaminchanks on 2018-05-06.
 */
class UndoableEditText : AppCompatEditText {

    private var mHistoryQueue = ArrayDeque<Action>() //记录队列
    private var mUndoHistoryQueue = ArrayDeque<Action>() //撤销记录队列

    private val mMaxQueueDepth = 120 //最多记录数

    private var isDoingAction = false //正在执行撤销/反撤销操作，防止每次文本的改变都触发文本监听器事件


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher{
            /**
             * 原始数字符串 s 自光标 start 开始的 count 个字符将被 after 个新的字符所代替
             * 被删除掉的文字只能在 #beforeTextChanged() 里获取到
             */
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (isDoingAction) return

                val end = start + count
                if (end > start && end <= s!!.length) {
                    val strTobeReplace = s.subSequence(start, end)
                    if (strTobeReplace.isNotEmpty()) { //被删除掉的文字
                        val action = Action(strTobeReplace, start, false)
                        addToHistory(mHistoryQueue, action)
                        mUndoHistoryQueue.clear()
                    }
                }
            }

            /**
             *  新的字符串 s 中自start开始的count 个字符已经替换了原先 自start开始的before个字符
             *  新增加的文字可以在 #onTextChanged() 中获取到
             */
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isDoingAction) return
                val end = start + count //如果count = 0 说明新的0个字符替换了原先的n个字符，应该是删除操作
                if (end > start) {
                    val strHasReplace = s!!.subSequence(start, end) //新增加的文字
                    if (strHasReplace.isNotEmpty()) {
                        val action = Action(strHasReplace, start, true)

                        addToHistory(mHistoryQueue, action)
                        mUndoHistoryQueue.clear()
                    }
                }

            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }


    /**
     * 如果超出队列上限，将队首元素出队，防止无限存放记录
     */
    private fun addToHistory(queue: ArrayDeque<Action>, action: Action) {
        while (queue.size > mMaxQueueDepth) {
            queue.removeFirst()
        }

        queue.addLast(action)
    }


    /**
     * 撤回
     */
    fun undo() {
        if (mHistoryQueue.isEmpty()) return
        //正在执行操作
        isDoingAction = true

        val action = mHistoryQueue.removeLast()
        addToHistory(mUndoHistoryQueue, action)

        if (action.isAdd) { //撤回删除
            editableText.delete(action.start, action.end)
            setSelection(action.start)
        } else { //撤回添加
            editableText.insert(action.start, action.charSequence)
            setSelection(action.end)
        }

        isDoingAction = false
    }

    /**
     * 反撤回
     */
    fun redo() {
        if (mUndoHistoryQueue.isEmpty()) return

        isDoingAction = true

        val action = mUndoHistoryQueue.removeLast()
        addToHistory(mHistoryQueue, action)
        if (action.isAdd) { //恢复添加
            editableText.insert(action.start, action.charSequence)
            setSelection(action.end)
        } else { //恢复删除
            editableText.delete(action.start, action.end)
            setSelection(action.start)
        }

        isDoingAction = false
    }


    companion object {
        data class Action(var charSequence: CharSequence,
                          var start: Int,
                          var isAdd: Boolean
                          ) {

            var end: Int = start + charSequence.length
                    private set

        }
    }
}