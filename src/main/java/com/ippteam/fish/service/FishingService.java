package com.ippteam.fish.service;

import com.ippteam.fish.entity.nosql.mongodb.Fishing;

import java.util.List;

/**
 * Created by isunimp on 16/11/23.
 */
public interface FishingService {

    void add(Fishing fishing);

    List<Fishing> near(final double longitude, final double latitude, double maxDistance) throws Exception;

}
