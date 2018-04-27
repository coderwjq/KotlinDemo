package com.coderwjq.kotlindemo.domain

/**
 * Created by wangjiaqi on 2018/4/26
 */
data class ForecastList(val city: String, val country: String, val dailyForecast: List<Forecast>) {
    operator fun get(position: Int): Forecast = dailyForecast[position]

    fun size() = dailyForecast.size
}

data class Forecast(val date: String, val description: String, val high: Int, val low: Int, val iconUrl: String)