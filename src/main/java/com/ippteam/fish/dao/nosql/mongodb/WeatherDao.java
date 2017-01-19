package com.ippteam.fish.dao.nosql.mongodb;

import com.ippteam.fish.dao.nosql.mongodb.util.MongoBaseDao;
import com.ippteam.fish.entity.nosql.mongodb.Weather;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static java.util.Calendar.DAY_OF_MONTH;

/**
 * Created by isunimp on 16/12/1.
 */
@Repository("WeatherDao")
public class WeatherDao extends MongoBaseDao<Weather> {

    public Weather last(String city) {
        Criteria criteria = new Criteria("date").gte(getTime()).and("data.result.city").is(city);
        Query query = new Query(criteria).with(new Sort(Sort.Direction.DESC, "date"));
        List<Weather> weathers = mongoTemplate.find(query, Weather.class);
        return weathers != null && weathers.size() > 0 ? weathers.get(0) : null;
    }

    /**
     * 获取查询时间
     *
     * @return
     */
    private Date getTime() {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, -2);
        return calendar.getTime();
    }
}
