package com.coderwjq.kotlindemo

import com.coderwjq.kotlindemo.domain.ForecastDataMapper
import com.coderwjq.kotlindemo.domain.ForecastList
import com.google.gson.Gson

/**
 * Created by wangjiaqi on 2018/4/26
 */
class RequestForecastCommand(val zipCode: Long) : Command<ForecastList> {

    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode, Gson())

        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }

}