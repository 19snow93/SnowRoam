package com.rhythm7.core.crash

import android.util.Log
import com.example.timber.CrashHandler
import com.rhythm7.core.utlis.Logger

/**
 * Created by Jaminchanks on 2018-03-14.
 */
public class CrashReportingTree : Logger.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        CrashHandler.log(priority, tag!!, message)

        if (t != null) {
            if (priority == Log.ERROR) {
                CrashHandler.logError(t)
            } else if (priority == Log.WARN) {
                CrashHandler.logWarning(t)
            }
        }
    }
}