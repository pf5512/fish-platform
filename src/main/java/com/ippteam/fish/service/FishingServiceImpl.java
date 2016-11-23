package com.ippteam.fish.service;

import com.ippteam.fish.dao.nosql.mongodb.FishingDao;
import com.ippteam.fish.entity.nosql.mongodb.Fishing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by isunimp on 16/11/23.
 */
@Service("FishingService")
public class FishingServiceImpl implements FishingServise {

    @Autowired
    FishingDao fishingDao;

    public void add(Fishing fishing) {
        fishingDao.insert(fishing);
    }

    public List<Fishing> near(final double longitude, final double latitude, double maxDistance) throws Exception {
        return fishingDao.near(longitude, latitude, maxDistance);
    }
}