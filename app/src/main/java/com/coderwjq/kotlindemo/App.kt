package com.coderwjq.kotlindemo

import android.app.Application

/**
 * Created by wangjiaqi on 2018/4/27
 */
class App : Application() {
    companion object {
        var instance: App by MainActivity.DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}