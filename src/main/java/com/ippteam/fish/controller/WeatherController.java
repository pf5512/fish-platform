package com.ippteam.fish.controller;

import com.ippteam.fish.util.api.pojo.Result;
import com.ippteam.fish.util.api.version.ApiVersion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by pactera on 16/10/27.
 */

@Controller
@RequestMapping("/api/{version}")
public class WeatherController extends BaseController {

    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    @ResponseBody
    @ApiVersion(1)
    public Result weather() {
        return null;
    }
}
