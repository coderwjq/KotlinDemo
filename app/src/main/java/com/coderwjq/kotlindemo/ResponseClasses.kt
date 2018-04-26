package com.coderwjq.kotlindemo

/**
 * Created by wangjiaqi on 2018/4/26
 */
data class Coordinates(val lon: Float, val lat: Float)

data class City(val id: Long, val name: String, val coord: Coordinates, val country: String, val population: Int)

data class ForecastResult(val city: City, val list: List<Forecast>)

/**
 * 额外的函数
 *
 * equals：通过比较两个对象的属性来确保它们是相同的
 * hashCode：
 * copy：
 */
data class Forecast(val dt: Long, val temp: Temperature, val pressure: Float, val humidity: Int,
                    val weather: List<Weather>, val speed: Float, val deg: Int, val clouds: Int, val rain: Float)

data class Temperature(val day: Float, val min: Float, val max: Float, val night: Float, val eve: Float, val morn: Float)

data class Weather(val id: Long, val main: String, val description: String, val icon: String)