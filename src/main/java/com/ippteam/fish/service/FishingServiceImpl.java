package com.ippteam.fish.service;

import com.ippteam.fish.dao.nosql.mongodb.FishingDao;
import com.ippteam.fish.entity.nosql.mongodb.Fishing;
import com.ippteam.fish.service.util.ReportServiceImpl;
import com.ippteam.fish.service.util.ReportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by isunimp on 16/11/23.
 */
@ReportType("FISHING")
@Service("FishingService")
public class FishingServiceImpl extends ReportServiceImpl implements FishingService {

    @Autowired
    FishingDao fishingDao;

    public void add(Fishing fishing) {
        fishingDao.insert(fishing);
    }

    public List<Fishing> near(final double longitude, final double latitude, double maxDistance) throws Exception {
        return fishingDao.near(longitude, latitude, maxDistance);
    }

    public void banned(String id) {
        fishingDao.banned(id);
    }
}
