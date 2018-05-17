package com.rhythm7.core.utlis

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader

/**
 * Created by Jaminchanks on 2018-03-28.
 */
object ShellUtil {
    private val LINE_SEPARATOR = System.lineSeparator()

    /**
     * @param commands 命令
     * @param isRoot 是否需要使用root权限
     * @param isNeedResultMsg 是否需要返回结果
     */
    fun execCmd(vararg commands: String, isRoot: Boolean, isNeedResultMsg: Boolean = true): CommandResult {
        var resultCode = -1
        if (commands.isEmpty()) {
            return CommandResult(resultCode, null, null)
        }

        val successMsg = StringBuilder()
        val errorMsg = StringBuilder()

        val process = Runtime.getRuntime().exec(if (isRoot) "su" else "sh")
        val os = DataOutputStream(process.outputStream)
        commands.forEach {
            os.write(it.toByteArray())
            os.writeBytes(LINE_SEPARATOR)
            os.flush()
        }
        os.writeBytes("exit $LINE_SEPARATOR")
        os.flush()
        resultCode = process.waitFor()
        if (isNeedResultMsg) {

            val successResult = BufferedReader(InputStreamReader(process.inputStream, "UTF-8"))
            val errorResult = BufferedReader(InputStreamReader(process.errorStream, "UTF-8"))
            successResult.takeIf { it.ready() }
                    ?.apply { successMsg.append(LINE_SEPARATOR) }
                    ?.forEachLine {
                        successMsg.append(it)
                        successMsg.append(LINE_SEPARATOR)
                    }

            errorResult.takeIf { it.ready() }
                    ?.apply { errorMsg.append(LINE_SEPARATOR) }
                    ?.forEachLine {
                        errorMsg.append(it)
                        errorMsg.append(LINE_SEPARATOR)
                    }

            process?.destroy()
        }

        return CommandResult(resultCode, successMsg.toString(), errorMsg.toString())
    }


    data class CommandResult(var resultCode: Int, var successMsg: String?, var errorMsg: String?)
}


