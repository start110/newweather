package com.newweather.app.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by Mr.Qing on 2017/10/28.
 * 县的实体类
 */

public class County extends DataSupport {

    private int id;
    private String countyName;

    //天气的id
    private String weatherId;

    //所属市的id
    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
