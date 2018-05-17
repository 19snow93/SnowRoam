package com.rhythm7.core.utlis

import android.util.Log
import com.rhythm7.core.BuildConfig
import com.rhythm7.core.R
import com.rhythm7.core.utlis.ext.resIdToString
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间处理相关的工具类
 * Created by chenmingzhen on 16-7-21.
 */
object TimeUtil {
    private val TAG = TimeUtil::class.java.simpleName
    private val weeks = arrayOf(R.string.Sun.resIdToString(),
            R.string.Mon.resIdToString(),
            R.string.Tues.resIdToString(),
            R.string.Wed.resIdToString(),
            R.string.Thur.resIdToString(),
            R.string.Fri.resIdToString(),
            R.string.Sat.resIdToString())

    fun getSimpleUserCanViewTime(timestamp: Long): String {
        var result: String?
        result = getTodayUserViewTime(timestamp)

        if (result == null) {
            result = getYesterDayUserViewTime(timestamp, false)
        }

        if (result == null) {
            result = getThisWeekUserViewTime(timestamp, false)
        }

        if (result == null) {
            result = getThisYearUserViewTime(timestamp, false)
        }

        if (result == null) {
            result = getYearsBeforeUserViewTime(timestamp, false)
        }

        Logger.d(TAG, result)

        return result
    }

    fun getUserCanViewTime(timestamp: Long): String {

        var result: String?
        result = getTodayUserViewTime(timestamp)

        if (result == null) {
            result = getYesterDayUserViewTime(timestamp, true)
        }

        if (result == null) {
            result = getThisWeekUserViewTime(timestamp, true)
        }

        if (result == null) {
            result = getThisYearUserViewTime(timestamp, true)
        }

        if (result == null) {
            result = getYearsBeforeUserViewTime(timestamp, true)
        }

        Logger.d(TAG, result)

        return result
    }

    private fun getThisYearUserViewTime(timestamp: Long, time: Boolean): String? {
        val thisYear = Calendar.getInstance()

        thisYear.set(Calendar.MONTH, 0)
        thisYear.set(Calendar.DAY_OF_MONTH, 0)
        thisYear.set(Calendar.HOUR_OF_DAY, 0)
        thisYear.set(Calendar.MINUTE, 0)
        thisYear.set(Calendar.SECOND, 0)
        thisYear.set(Calendar.MILLISECOND, 0)
        thisYear.add(Calendar.DAY_OF_MONTH, 1)

        if (timestamp >= thisYear.timeInMillis) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timestamp

            val dateFormat: DateFormat?
            if (time) {
                dateFormat = SimpleDateFormat("M月d日 HH:mm")
            } else {
                dateFormat = SimpleDateFormat("M月d日")
            }
            return dateFormat.format(calendar.time)
        }
        return null
    }

    private fun getYearsBeforeUserViewTime(timestamp: Long, time: Boolean): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        val dateFormat: DateFormat?
        if (time) {
            dateFormat = SimpleDateFormat("yyyy年M月d日 HH:mm")
        } else {
            dateFormat = SimpleDateFormat("yyyy年M月d日")
        }
        return dateFormat.format(calendar.time)
    }

    /**
     * 返回本周的日期显示
     *
     * @param timestamp
     * @return
     */
    private fun getThisWeekUserViewTime(timestamp: Long, time: Boolean): String? {
        val thisWeek = Calendar.getInstance()

        thisWeek.set(Calendar.HOUR_OF_DAY, 0)
        thisWeek.set(Calendar.MINUTE, 0)
        thisWeek.set(Calendar.SECOND, 0)
        thisWeek.set(Calendar.MILLISECOND, 0)

        val dayOfWeek = thisWeek.get(Calendar.DAY_OF_WEEK)
        thisWeek.add(Calendar.DAY_OF_MONTH, 1 - dayOfWeek)

        val thisWeekTimeStamp = thisWeek.timeInMillis
        if (timestamp >= thisWeekTimeStamp) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timestamp

            val prefix = weeks[calendar.get(Calendar.DAY_OF_WEEK) - 1]

            if (time) {
                val dateFormat = SimpleDateFormat("HH:mm")
                return prefix + " " + dateFormat.format(calendar.time)
            } else {
                return prefix
            }


        }
        return null
    }

    /**
     * 前一天：昨天 09:10:01
     *
     * @param timestamp
     * @return
     */
    private fun getYesterDayUserViewTime(timestamp: Long, time: Boolean): String? {
        val yesterday = Calendar.getInstance()

        yesterday.set(Calendar.HOUR_OF_DAY, 0)
        yesterday.set(Calendar.MINUTE, 0)
        yesterday.set(Calendar.SECOND, 0)
        yesterday.set(Calendar.MILLISECOND, 0)

        //昨天0点
        yesterday.add(Calendar.DAY_OF_MONTH, -1)
        val yesterdayTimestamp = yesterday.timeInMillis

        if (timestamp >= yesterdayTimestamp) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timestamp

            val prefix = R.string.yesterday.resIdToString()
            if (time) {
                val dateFormat = SimpleDateFormat("HH:mm")
                return prefix + " " + dateFormat.format(calendar.time)
            } else {
                return prefix
            }
        }
        return null
    }

    /**
     * 时间精确至秒，1至9时需要在加零，其他不需加零。
     * 时间分段早上（5点-8点）、上午（9点-12点）、下午（13点-18点）、晚上（19点-23点）、凌晨（00点-5点）。
     *
     * @param timestamp
     * @return
     */
    private fun getTodayUserViewTime(timestamp: Long): String? {
        val today = Calendar.getInstance()

        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)

        //今天0点
        val todayTimestamp = today.timeInMillis

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        if (timestamp >= todayTimestamp) {
            var prefix: String? = null
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            when (hour) {
                in 0..5 -> prefix = R.string.early_morning.resIdToString()
                in 6..8 -> prefix = R.string.morning.resIdToString()
                in 9..12 -> prefix = R.string.later_morning.resIdToString()
                in 13..18 -> prefix = R.string.afternoon.resIdToString()
                in 19..23 -> prefix = R.string.evening.resIdToString()
            }

            val dateFormat = SimpleDateFormat("HH:mm")
            return prefix + " " + dateFormat.format(calendar.time)

        }
        return null
    }

}
