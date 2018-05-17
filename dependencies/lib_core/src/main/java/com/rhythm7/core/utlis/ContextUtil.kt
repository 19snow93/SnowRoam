package com.rhythm7.core.utlis

import android.content.Context
import android.widget.Toast

/**
 * Created by Jaminchanks on 2018-03-15.
 */

public lateinit var mAppContext: Context

fun showToast(msg: String?) = Toast.makeText(mAppContext, msg, Toast.LENGTH_SHORT).show()