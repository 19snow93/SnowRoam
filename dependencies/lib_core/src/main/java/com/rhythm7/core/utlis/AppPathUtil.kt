package com.rhythm7.core.utlis

import android.os.Environment
import android.text.TextUtils
import com.rhythm7.core.config.AppConstants
import com.rhythm7.core.utlis.ext.getFileMkDirPath
import com.rhythm7.core.utlis.ext.read
import java.io.File
import java.util.ArrayList

/**
 * 用于操作文件夹路径的类
 * Created by Jaminchanks on 2018-03-14.
 */
object AppPathUtil {
    private val TAG = AppPathUtil::class.java.simpleName

    //App文件夹的路径,包括该App的文件名
    private lateinit var APP_DIR: String

    //App文件下载的路径，DownloadManager会提供外置内存卡的路径
    private val APP_FOLDER = File.separator + AppConstants.APP_FOLDER + File.separator

    //相册路径
    private val DCIM = File.separator + Environment.DIRECTORY_DCIM + File.separator + "Camera" + File.separator

    //图片压缩和缓存路径
    private val APP_IMG_CACHE = "PICTURECACHE"

    //系统内部缓存路径
    private var CACHE_DIR = "CACHE"

    //奔溃日志的文件路径
    private val CRASH_LOG = "CRASH_LOG"

    //普通日志的文件位置
    private val LOG_DIR = "LOG"

    //http缓存的位置
    private val HTTP_CACHE_PATH = "HTTP_CACHE"

    init {
        if (TextUtils.isEmpty(APP_DIR)) {
            val sdCardExist = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
            if (sdCardExist) {
                APP_DIR = Environment.getExternalStorageDirectory().toString() + APP_FOLDER
            } else {
                val path = getInternalPath()
                if (!TextUtils.isEmpty(path)) {
                    APP_DIR = path + APP_FOLDER
                } else {
                    APP_DIR = CACHE_DIR
                }
            }
        }
    }


    fun getDcim(): String {
        return File(APP_DIR + DCIM).getFileMkDirPath()
    }

    /**
     * 获取图片缓存路径
     *
     * @return
     */
    fun getAppImgCache(): String {
        return File(APP_DIR + APP_IMG_CACHE).getFileMkDirPath()
    }

    fun getAppDir(): String {
        return File(APP_DIR).getFileMkDirPath()
    }

    /**
     * 奔溃日志文件夹路径
     */
    fun getCrashLogDir(): String {
        return File(APP_DIR + CRASH_LOG).getFileMkDirPath()
    }

    /**
     * 普通日志文件夹路径
     */
    fun getLogDir(): String {
        return File(APP_DIR + LOG_DIR).getFileMkDirPath()
    }


    /**
     * 获取内部存储的地址, 不同手机会使用不同名称的目录, 此处是先获得挂载点然后进行判断筛选
     */
    private fun getInternalPath(): String {
        //所有挂载的地址
        val mountList = getDevMountList()
        return mountList
                .map { File(it) }
                .firstOrNull {
                    it.isDirectory && it.canWrite() && it.canRead() && it.canExecute()
                }
                ?.absolutePath
                ?: ""
    }

    /**
     * 遍历/etc/vold.fstab 获取全部的Android的挂载点信息
     *
     * @return
     */
    private fun getDevMountList(): ArrayList<String> {
        val contentByte = File("/etc/vold.fstab").read()
        val toSearch = String(contentByte).split(" ".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        return toSearch.indices
                .filter { toSearch[it].contains("dev_mount") && File(toSearch[it + 2]).exists() }
                .mapTo(ArrayList()) { toSearch[it + 2] }
    }


}