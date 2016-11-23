package com.ippteam.fish.controller;

import com.ippteam.fish.entity.nosql.mongodb.Fishing;
import com.ippteam.fish.service.FishingService;
import com.ippteam.fish.util.api.pojo.Result;
import com.ippteam.fish.util.api.version.ApiVersion;
import org.apache.ibatis.executor.ReuseExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by isunimp on 16/11/23.
 */

@Controller("FishingController")
@RequestMapping("/api/{version}/fishing")
public class FishingController {

    @Autowired
    FishingService fishingService;

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/near", method = RequestMethod.GET)
    public Result near(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double maxDistance) throws Exception {
        List<Fishing> fishings = fishingService.near(longitude, latitude, maxDistance);
        return new Result(0, null, fishings);
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Fishing fishing) throws Exception {
        fishingService.add(fishing);
        return new Result(0, null, true);
    }
}