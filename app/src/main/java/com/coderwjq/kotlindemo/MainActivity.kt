package com.coderwjq.kotlindemo

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {
    val TAG = javaClass.simpleName

    private val items = listOf(
            "Mon 6/23 - Sunny - 31/17",
            "Tue 6/24 - Foggy - 21/8",
            "Wed 6/25 - Cloudy - 22/17",
            "Thurs 6/26 - Rainy - 18/11",
            "Fri 6/27 - Foggy - 21/10",
            "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
            "Sun 6/29 - Sunny - 20/7"
    )

    // 如果不指定函数的返回值，会返回Unit，与Java中的void类似，但是Unit是一个真正的对象
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        toast("不带时间参数")
//        toast("携带时间参数", Toast.LENGTH_LONG)
        niceToast(message = "Nice Toast", length = Toast.LENGTH_LONG)

        forecastList.layoutManager = LinearLayoutManager(this)

        /**
         * Kotlin中的基本类型：
         *
         * 1. Kotlin中数字类型不会自动转换，不同数字类型直接转换必须要做明确类型转换
         * 2. Char不能直接作为数字来处理
         * 3. 位运算使用 and 和 or
         * 4. 可以省略变量类型，让编译器自己去推断
         */

        /**
         * Kotlin中的变量：
         *
         * 1. var，可修改变量
         * 2. val，不可修改变量（建议尽量使用val来修饰）
         */

        /**
         * Kotlin中的属性：
         *
         * 属性会在字段上加上setter和getter方法，当然也可以修改为自定义代码，并且不修改存在的代码
         */
        val person = Person()
        person.name = "yoda"
        Log.i(TAG, person.name)

        /**
         * 扩展函数：不需要在调用方法的时候把整个对象作为参数传入
         * 可以使用this关键字和调用所有public方法
         *
         * 扩展函数也可以是一个属性，例如TextView的text属性
         */
        toast("扩展函数")

        doAsync {
            Request("http://www.baidu.com").run()

            uiThread {
                niceToast("Request performed")
            }
        }

        doAsync {
            val result = RequestForecastCommand(94043).execute()

            uiThread {
                forecastList.adapter = ForecastListAdapter(result) { niceToast(it.date) }
            }
        }

        /**
         * 一个lambda表达式通过参数的形式被定义在箭头的左边（被圆括号包围），然后在箭头的右边返回结果值
         *
         * with函数的内部实现
         *
         * inline fun <T> with(t: T, body: T.() -> Unit) { t.body() }
         *
         * 内联函数：会在编译的时候被替换掉，而不是真正的方法调用
         * 这在一些情况下可以减少内存分配和运行时开销
         */

        /**
         * Kotlin中默认的修饰符是public
         *
         * 1. private
         * 2. protected
         * 3. internal:对整个module可见
         * 4. public
         *
         * 所有构造函数默认都是public的，可以使用private constructor修饰把构造函数修改为private
         */
    }

    /**
     * backing field包括field和value，只能在属性访问器内部访问
     */
    class Person {
        var name: String = ""
            get() = field.toUpperCase()
            set(value) {
                field = "Name = $value"
            }
    }

    fun add(x: Int, y: Int): Int {
        return x + y
    }

    // 如果返回的结果可以使用一个表达式计算出来，你可以不使用括号而是使用等号
    fun add(x: Int, y: Int, z: Int): Int = x + y + z

//    fun toast(message: String, length: Int = Toast.LENGTH_SHORT) {
//        Toast.makeText(this, message, length).show()
//    }

    fun niceToast(message: String, tag: String = MainActivity::class.java.simpleName, length: Int = Toast.LENGTH_SHORT) {
        // 如果模板表达式比较复杂，可以使用一对大括号括起来：e.g Your name is ${user.name}
        Toast.makeText(this, "[$localClassName] $message", length).show()
    }

    fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }
}
