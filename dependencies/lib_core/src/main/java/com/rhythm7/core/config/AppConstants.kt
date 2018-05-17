package com.rhythm7.core.config

import com.rhythm7.core.BuildConfig
import java.io.IOException
import java.io.InputStream
import java.util.Properties

/**
 * 读取assets/config.properties里的属性
 * Created by jaminchanks on 2017/2/27.
 */

object AppConstants {
    lateinit var APP_URL: String
    lateinit var APP_FOLDER: String

    init {
        val properties = Properties()
        try {
            val inputStream = AppConstants::class.java.getResourceAsStream("/assets/config.properties")
            properties.load(inputStream)
            APP_URL = properties.getProperty("APP_URL")
            APP_FOLDER = properties.getProperty("APP_FOLDER")
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

}
