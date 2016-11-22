package com.ippteam.fish.dao.nosql.mongodb;

import com.ippteam.fish.entity.nosql.mongodb.FishingGround;
import com.ippteam.fish.util.JSON;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.*;

import javax.annotation.Resource;

/**
 * Created by isunimp on 16/11/22.
 */

@Repository("FishingGroundDao")
public class FishingGroundDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public void insert(FishingGround fishingGround, String collectionName) {
        mongoTemplate.insert(fishingGround, collectionName);
    }

    public List<FishingGround> near(final double longitude, final double latitude, double maxDistance) throws Exception {
        DBCollection collection = mongoTemplate.getCollection("fishing_ground");

        BasicDBList coordinates = new BasicDBList();
        coordinates.add(longitude);
        coordinates.add(latitude);

        BasicDBObject geometry = new BasicDBObject();
        geometry.put("type", "Point");
        geometry.put("coordinates", coordinates);

        BasicDBObject near = new BasicDBObject();
        near.put("$geometry", geometry);
        near.put("$maxDistance", maxDistance);

        BasicDBObject loc = new BasicDBObject();
        loc.put("$near", near);

        BasicDBObject conditionBbject = new BasicDBObject("loc", loc);

        DBCursor cursor = collection.find(conditionBbject);
        List<FishingGround> fishingGrounds = new ArrayList<FishingGround>();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            fishingGrounds.add(FishingGround.newFishingGround(dbObject));
        }
        return fishingGrounds;
    }
}
