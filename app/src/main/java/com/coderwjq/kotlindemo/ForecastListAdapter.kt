package com.coderwjq.kotlindemo

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.coderwjq.kotlindemo.domain.ForecastList

/**
 * Created by wangjiaqi on 2018/4/25
 */
class ForecastListAdapter(val weekForecast: ForecastList) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /**
         * with函数：
         *
         * 一个非常有用的函数，包含着kotlin标准库中
         *
         * 接收一个对象和一个扩展函数作为参数，然后使这个对象扩展这个函数
         *
         * 这表示所有我们在括号中编写的代码都是作为第一个参数的一个扩展函数
         *
         * 我们就可以像作为this一样使用它的public方法和属性
         *
         * 当我们针对同一个对象做很多操作时，这个非常有利于简化代码
         */
        with(weekForecast.dailyForecast[position]) {
            holder.textView.text = "$date - $description - $high/$low"
        }
    }

    override fun getItemCount(): Int = weekForecast.dailyForecast.size

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}