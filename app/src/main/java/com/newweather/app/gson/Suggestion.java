package com.newweather.app.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mr.Qing on 2017/10/29.
 * 有关天气的建议
 */

public class Suggestion {

    @SerializedName("comf")
    public  Comfort comfort;

    @SerializedName("cw")
    public CarWash carWash;

    @SerializedName("sport")
    public Sport sport;

    @SerializedName("air")
    public Air air;

    @SerializedName("drsg")
    public Clothing clothing;

    @SerializedName("trav")
    public Travel travel;


    /**
     * 舒适的情况
     */
    public class  Comfort{

        @SerializedName("txt")
        public String info;

        @SerializedName("brf")
        public String brf;
    }

    /**
     * 洗车的建议
     */
    public class CarWash{
        @SerializedName("txt")
        public String info;

        @SerializedName("brf")
        public String brf;

    }

    /**
     * 运动的建议
     */
    public class Sport{

        @SerializedName("txt")
        public String info;

        @SerializedName("brf")
        public String brf;
    }


    /**
     * 空气情况
     */
    public class Air{
        @SerializedName("txt")
        public String info;

        @SerializedName("brf")
        public String brf;
    }


    /**
     * 穿着
     */
    public class Clothing{
        @SerializedName("txt")
        public String info;

        @SerializedName("brf")
        public String brf;
    }


    /**
     * 旅行
     */
    public class Travel{
        @SerializedName("txt")
        public String info;

        @SerializedName("brf")
        public String brf;
    }

}
