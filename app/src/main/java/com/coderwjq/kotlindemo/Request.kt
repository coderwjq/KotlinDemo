package com.coderwjq.kotlindemo

import android.util.Log
import java.net.URL

/**
 * Created by wangjiaqi on 2018/4/26
 */
class Request(val url: String) {
    fun run() {
        val forecastJsonStr = URL(url).readText()
        Log.d(javaClass.simpleName, "forecastJsonStr = $forecastJsonStr")
    }
}