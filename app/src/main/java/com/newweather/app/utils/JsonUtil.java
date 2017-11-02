package com.newweather.app.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.newweather.app.entity.City;
import com.newweather.app.entity.County;
import com.newweather.app.entity.Province;
import com.newweather.app.gson.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mr.Qing on 2017/10/28.
 * json工具类
 */

public class JsonUtil {

    /**
     *
     * @param response
     * @return
     * 解析省级数据
     */
    public static boolean handleProvinceResponse(String response){

        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray arrayProvince = new JSONArray(response);
                for (int i=0;i<arrayProvince.length();i++){
                    JSONObject objectProvince =arrayProvince.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(objectProvince.getString("name"));
                    province.setProvinceCode(objectProvince.getInt("id"));
                    province.save();
                }
                return true;

            }catch (JSONException e){
                e.printStackTrace();

            }
        }
        return false;
    }

    /**
     *
     * @param response
     * @param provinceId
     * @return
     * 解析市级数据
     */
    public static boolean handleCityResponse(String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray arrayCity = new JSONArray(response);
                for (int i=0;i<arrayCity.length();i++){
                    JSONObject objectCity =arrayCity.getJSONObject(i);
                    City city = new City();
                    city.setCityName(objectCity.getString("name"));
                    city.setCityCode(objectCity.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();

                }
                return true;

            }catch (JSONException e){
                e.printStackTrace();

            }
        }
        return false;
    }

    /**
     *
     * @param response
     * @param cityId
     * @return
     * 解析县级数据
     */
    public static boolean handleCountyResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray arrayCounty = new JSONArray(response);
                for (int i=0;i<arrayCounty.length();i++){
                    JSONObject objectCounty =arrayCounty.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(objectCounty.getString("name"));
                    county.setWeatherId(objectCounty.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();

                }
                return true;

            }catch (JSONException e){
                e.printStackTrace();

            }
        }
        return false;
    }

    public static Weather jsonWeatherResponse(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return  new Gson().fromJson(weatherContent,Weather.class);

        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
}
