package com.newweather.app.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mr.Qing on 2017/10/29.
 * 天气的总实体
 */

public class Weather {


    /**
     * 请求状态，ok-请求成功
     */
    public String status;

    /**
     * 城市的一些基本信息
     */
    public Basic basic;

    /**
     * 空气质量情况
     */
    public  AQI aqi;

    /**
     * 天气的信息
     */
    public Now now;

    /**
     * 天气相关的生活建议
     */
    public  Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;

}
