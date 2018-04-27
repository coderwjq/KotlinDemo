package com.coderwjq.kotlindemo

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.coderwjq.kotlindemo.domain.Forecast
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
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

        val rvForecastList = find<RecyclerView>(R.id.rv_forecast_list)
        rvForecastList.layoutManager = LinearLayoutManager(this)

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
                rvForecastList.adapter = ForecastListAdapter(result,
                        // 创建匿名内部类
                        object : ForecastListAdapter.OnItemClickListener {
                            override fun invoke(forecast: Forecast) {
                                niceToast(forecast.date)
                            }
                        })
            }
        }
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
