package com.newweather.app.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mr.Qing on 2017/10/29.
 * 天气预报
 */

public class Forecast {

    /**
     * 日期
     */
    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    @SerializedName("wind")
    public Wind wind;

    /**
     * 一天的最大、最小温度
     */
    public class  Temperature{
        public String max;
        public String min;
    }

    /**
     * 天气情况
     */
    public class  More{
        @SerializedName("txt_d")
        public String info;
    }


    /**
     * 风的信息
     */
    public class Wind{
       //风向
        @SerializedName("dir")
        public String direction;

        //风的大小
        @SerializedName("sc")
        public String size;

    }

}
