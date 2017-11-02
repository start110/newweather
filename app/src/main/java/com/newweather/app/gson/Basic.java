package com.newweather.app.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mr.Qing on 2017/10/29.
 * 关于城市的一些天气的信息
 */

public class Basic {

    /**
     * 城市名
     */
    @SerializedName("city")
    public String cityName;

    /**
     * 天气id
     */
    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{

        /**
         * 天气的更新时间
         */
        @SerializedName("loc")
        public String updateTime;

    }



}
