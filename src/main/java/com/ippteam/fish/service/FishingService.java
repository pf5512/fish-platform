package com.ippteam.fish.service;

import com.ippteam.fish.entity.nosql.mongodb.Fishing;
import com.ippteam.fish.service.util.ReportService;

import java.util.List;

/**
 * Created by isunimp on 16/11/23.
 */
public interface FishingService extends ReportService {

    Fishing fishingById(String id);

    void add(Fishing fishing);

    List<Fishing> near(final double longitude, final double latitude, double maxDistance) throws Exception;

}
