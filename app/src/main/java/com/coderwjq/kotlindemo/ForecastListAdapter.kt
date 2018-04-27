package com.coderwjq.kotlindemo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coderwjq.kotlindemo.domain.Forecast
import com.coderwjq.kotlindemo.domain.ForecastList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_forecast.view.*

/**
 * Created by wangjiaqi on 2018/4/25
 */
class ForecastListAdapter(val weekForecast: ForecastList,
                          val itemClick: (Forecast) -> Unit) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, itemClick)
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
//        with(weekForecast[position]) {
//        }

        holder.bindForecast(weekForecast[position])
    }

    override fun getItemCount(): Int = weekForecast.size()

    class ViewHolder(itemView: View, val itemClick: (Forecast) -> Unit) : RecyclerView.ViewHolder(itemView) {

        fun bindForecast(forecast: Forecast) {
            with(forecast) {
                Picasso.with(itemView.ctx).load(iconUrl).into(itemView.iconView)

                itemView.dateText.text = date
                itemView.descriptionText.text = description
                itemView.maxTemperature.text = "${high}º"
                itemView.minTemperature.text = "${low}º"
                itemView.setOnClickListener {
                    itemClick(forecast)
                }
            }
        }
    }

    public interface OnItemClickListener {
        operator fun invoke(forecast: Forecast)
    }
}