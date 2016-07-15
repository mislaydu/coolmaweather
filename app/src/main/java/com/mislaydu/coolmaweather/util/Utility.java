package com.mislaydu.coolmaweather.util;

import android.text.TextUtils;

import com.mislaydu.coolmaweather.db.CoolmaWeatherDB;
import com.mislaydu.coolmaweather.model.City;
import com.mislaydu.coolmaweather.model.County;
import com.mislaydu.coolmaweather.model.Province;

/**
 * 解析和处理服务器返回的数据(JSON)
 */
public class Utility {
    /**
     * 解析服务器返回的省级数据
     * @param coolmaWeatherDB
     * @param response
     * @return
     */
    public  synchronized  static boolean handleProvincesResponse(CoolmaWeatherDB coolmaWeatherDB,String response){
        if(!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if(allProvinces!=null&&allProvinces.length>0){
                for(String p:allProvinces){
                    String[] array = p.split("\\|");      // 拆分省级代码与省份
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    // 将解析出来的数据存储到Province表
                    coolmaWeatherDB.saveProvince(province);
                }
                return  true;
            }
        }
        return false;
    }

    /**
     * 解析服务器返回的市级数据
     * @param coolmaWeatherDB
     * @param response
     * @param provinceId
     * @return
     */
    public static  boolean handleCitiesResponse(CoolmaWeatherDB coolmaWeatherDB,String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if(allCities!=null&&allCities.length>0){
                for(String c:allCities){
                    String[] array = c.split("\\|");      // 拆分省级代码与省份
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    // 将解析出来的数据存储到City表
                    coolmaWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析服务器返回的县级数据
     * @param coolmaWeatherDB
     * @param response
     * @param cityId
     * @return
     */
    public static boolean handleCountiesResponse(CoolmaWeatherDB coolmaWeatherDB,String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            String[] allCounties = response.split(",");
            if(allCounties!=null&&allCounties.length>0){
                for(String c:allCounties){
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    // 将解析出来的数据存储到County表
                    coolmaWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
}
