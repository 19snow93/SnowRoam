package com.rhythm7.core.utlis.ext

import com.rhythm7.core.utlis.Logger
import java.io.*

/**
 * Created by Jaminchanks on 2018-03-14.
 */


/**
 * 读取文件
 * @return
 */
fun File.read(): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()

    bufferedReader().forEachLine {
        byteArrayOutputStream.write(it.toByteArray())
    }

    return byteArrayOutputStream.toByteArray()
}


/**
 * 保存文件
 *
 * @param content
 */
fun File.save(content: ByteArray?) {
    if (content != null && content.size == 1)
        throw IllegalArgumentException("invalid image content")
    if (!parentFile.exists())
        parentFile.mkdirs()

    try {
        outputStream().write(content)
    } catch (e: Exception) {
        Logger.e(e)
    }
}


/**
 * 若文件目录不存在，创建其目录
 */
fun File.getFileMkDirPath(): String {
    if (!exists()) {
        mkdirs()
    }
    return path
}

/**
 * 写入文件
 *
 * @param `inputStream`
 */
@Throws(IOException::class)
fun File.writeTo(inputStream: InputStream) {
    if (!parentFile.exists())
        parentFile.mkdirs()

    if (exists())
        delete()

    val out = FileOutputStream(this)
    inputStream.bufferedReader().forEachLine {
        out.write(it.toByteArray())
    }

    out.flush()
    out.close()
}

