package com.coderwjq.kotlindemo.domain

import com.coderwjq.kotlindemo.Forecast
import com.coderwjq.kotlindemo.ForecastResult
import java.text.DateFormat
import java.util.*
import com.coderwjq.kotlindemo.domain.Forecast as ModelForecast

/**
 * Created by wangjiaqi on 2018/4/26
 */
class ForecastDataMapper {
    fun convertFromDataModel(forecastResult: ForecastResult): ForecastList {
        return ForecastList(forecastResult.city.name, forecastResult.city.country, convertForecastListToDomain(forecastResult.list))
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        // 循环集合，并且返回转换后的新的List
        return list.map { convertForecastItemToDomain(it) }
    }

    private fun convertForecastItemToDomain(forecast: Forecast): ModelForecast {
        return ModelForecast(convertDate(forecast.dt),
                forecast.weather[0].description,
                forecast.temp.max.toInt(),
                forecast.temp.min.toInt(),
                generateIconUrl(forecast.weather[0].icon))
    }

    private fun generateIconUrl(icon: String): String = "http://openweathermap.org/img/w/$icon.png"

    private fun convertDate(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return df.format(date * 1000)
    }
}