package com.ippteam.fish.controller;

import com.ippteam.fish.service.WeatherServiceImpl;
import com.ippteam.fish.util.api.pojo.Result;
import com.ippteam.fish.util.api.version.ApiVersion;
import com.mongodb.util.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * Created by pactera on 16/10/27.
 */

@Controller
@RequestMapping("/api/{version}/weather")
public class WeatherController extends BaseController {

    @Autowired
    WeatherServiceImpl weatherService;

    @ResponseBody
    @ApiVersion(1)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Result weather(@RequestParam String city) throws Exception {
        String data = weatherService.weather(city, new Date());
        Map m = com.ippteam.fish.util.JSON.parse(data, Map.class);
        return new Result(0, null, m);
    }
}
