package com.rhythm7.snowroam

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

//    private lateinit var classA1: ClassA

    public val classA2: ClassA? by lazy { ClassA(1) }

    public fun printClassA2() {
        getXX { println("hello world") }
        println(classA2?.a)
    }


    public fun getXX(action: ()->Unit) {
        action()
    }
}
