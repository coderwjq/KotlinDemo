package com.coderwjq.kotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
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

        val rvForecastList = findViewById<RecyclerView>(R.id.rv_forecast_list)
        rvForecastList.layoutManager = LinearLayoutManager(this)
        rvForecastList.adapter = ForecastListAdapter(items)
    }

    fun add(x: Int, y: Int): Int {
        return x + y
    }

    // 如果返回的结果可以使用一个表达式计算出来，你可以不使用括号而是使用等号
    fun add(x: Int, y: Int, z: Int): Int = x + y + z

    fun toast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, length).show()
    }

    fun niceToast(message: String, tag: String = MainActivity::class.java.simpleName, length: Int = Toast.LENGTH_SHORT) {
        // 如果模板表达式比较复杂，可以使用一对大括号括起来：e.g Your name is ${user.name}
        Toast.makeText(this, "[$localClassName] $message", length).show()
    }
}
