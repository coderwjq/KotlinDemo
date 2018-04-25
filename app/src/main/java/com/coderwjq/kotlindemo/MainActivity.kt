package com.coderwjq.kotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // 如果不指定函数的返回值，会返回Unit，与Java中的void类似，但是Unit是一个真正的对象
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_message.text = "hello apus"
    }

    fun add(x: Int, y: Int): Int {
        return x + y
    }

    // 如果返回的结果可以使用一个表达式计算出来，你可以不使用括号而是使用等号
    fun add(x: Int, y: Int, z: Int): Int = x + y + z
}
