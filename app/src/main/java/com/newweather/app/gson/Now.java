package com.newweather.app.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mr.Qing on 2017/10/29.
 * 城市的天气情况实体
 */

public class Now {

    /**
     * 温度
     */
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More{

        /**
         * 天气
         */
        @SerializedName("txt")
        public String info;
    }
}
