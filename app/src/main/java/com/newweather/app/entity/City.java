package com.newweather.app.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by Mr.Qing on 2017/10/28.
 * 城市的实体
 */

public class City extends DataSupport {

    private int id;
    private String cityName;

    //城市的代号
    private String cityCode;
    //当前市所属省份的id
    private int provinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
