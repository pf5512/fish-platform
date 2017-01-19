package com.ippteam.fish.service;

import com.ippteam.fish.dao.nosql.mongodb.WeatherDao;
import com.ippteam.fish.entity.nosql.mongodb.Weather;
import com.ippteam.fish.util.HttpUtils;
import com.ippteam.fish.util.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by isunimp on 16/11/23.
 */
@Service("WeatherService")
public class WeatherServiceImpl {

    @Autowired
    WeatherDao weatherDao;

    public Map weather(String city, Date date) throws Exception {
        Map data = null;
        // 获取缓存
        data = getByCache(city, date);
        if (data != null) return data;
        // 阿里云获取
        data = getByAliyun(city);
        if (data != null) {
            // 缓存
            Map map = JSON.parse(data, Map.class);
            if (map.get("status").equals("0")) {
                Weather weather = new Weather();
                weather.setDate(new Date());
                weather.setData(JSON.parse(data, Map.class));
                weatherDao.insert(weather);
            }
        }
        return data;
    }

    private Map getByCache(String city, Date date) throws Exception {
        Weather cacheWeather = weatherDao.last(city);
        if (cacheWeather == null) return null;

        Date cacheDate = cacheWeather.getDate();
        if (date.getTime() - cacheDate.getTime() > 2 * 60 * 60 * 1000) return null;

        return (HashMap) cacheWeather.getData();
    }

    private Map getByAliyun(String city) throws Exception {
        String host = "http://jisutianqi.market.alicloudapi.com";
        String path = "/weather/query";
        String method = "GET";

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE 80e60e43b9a544a68bafe2cdf1ddd7bf");

        Map<String, String> querys = new HashMap<String, String>();
        querys.put("city", city);
        // querys.put("citycode", "citycode");
        // querys.put("cityid", "cityid");

        HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
        return JSON.parse(EntityUtils.toString(response.getEntity()), Map.class);
    }
}
