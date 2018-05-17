package com.rhythm7.core.utlis

import android.app.ActivityManager
import android.content.Context
import android.content.Intent

import java.util.ArrayList

/**
 * Created by chenmingzhen on 16-8-18.
 */
object ServiceUtil {

    private const val MAX_RUNNING_SERVICE_NUM = 320 //获取到系统正在运行的服务数

    fun stopService(context: Context, className: Class<*>) {
        //判断服务是否还在运行，如果还在运行，退出
        if (ServiceUtil.isServiceWorking(context, className.name)) {
            Logger.i("服务已经启动了！！")
            val intent = Intent()
            intent.setClass(context, className)
            context.stopService(intent)
            Logger.i("info", "服务已经结束了！！")
        }
    }


    private fun isServiceWorking(context: Context, className: String): Boolean {
        val myManager = context.getSystemService(
                Context.ACTIVITY_SERVICE) as ActivityManager
        val runningService = myManager
                .getRunningServices(MAX_RUNNING_SERVICE_NUM) as ArrayList<ActivityManager.RunningServiceInfo>
        Logger.i("ServiceUtil", runningService.size.toString() + "个服务正在系统运行中")
        for (i in runningService.indices) {
            if (runningService[i].service.className.toString() == className) {

                Logger.i("ServiceUtil", "找到了相关的Service")
                return true
            }
        }
        return false
    }
}
