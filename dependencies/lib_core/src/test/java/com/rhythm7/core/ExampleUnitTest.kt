package com.rhythm7.core

import org.junit.Test
import java.io.File
import java.io.FileNotFoundException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val file = File("C:\\Users\\Jaminchanks\\Desktop\\新建文件夹")

        file.listFiles { dir, name ->
            println("$dir + $name")
            true
        }
    }


    @Throws(FileNotFoundException::class, IllegalArgumentException::class)
    fun eachFileRecurse(self: File,  closure: (File) -> Unit) {
        checkDir(self)
        val files = self.listFiles()
        if (files != null) {
            val var5 = files.size

            for (var6 in 0 until var5) {
                val file = files[var6]
                if (file.isDirectory) {
                     closure(file)
                    eachFileRecurse(file, closure)
                } else {
                    closure(file)
                }
            }

        }
    }

    @Throws(FileNotFoundException::class, IllegalArgumentException::class)
    private fun checkDir(dir: File) {
        if (!dir.exists()) {
            throw FileNotFoundException(dir.absolutePath)
        } else if (!dir.isDirectory) {
            throw IllegalArgumentException("The provided File object is not a directory: " + dir.absolutePath)
        }
    }
}

