package com.newweather.app.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by Mr.Qing on 2017/10/28.
 * 有个省份的实体
 */
//Litepal要继承DataSupport
public class Province extends DataSupport{
    private int id;

    //省份名
    private String provinceName;

    //记录省的代号
    private int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
