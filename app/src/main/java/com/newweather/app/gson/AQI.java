package com.newweather.app.gson;

/**
 * Created by Mr.Qing on 2017/10/29.
 * 城市的天气质量实体
 */

public class AQI {

    public AQICity city;
    public class AQICity{
        public String aqi;
        public String pm25;
        public String qlty; //污染程度
    }
}
