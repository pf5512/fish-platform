package com.ippteam.fish.dao.nosql.mongodb;

import com.ippteam.fish.entity.nosql.mongodb.Fishing;
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

@Repository("FishingDao")
public class FishingDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public void insert(Fishing fishing, String collectionName) {
        mongoTemplate.insert(fishing, collectionName);
    }

    public List<Fishing> near(final double longitude, final double latitude, double maxDistance) throws Exception {
        DBCollection collection = mongoTemplate.getCollection("fishing");

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
        List<Fishing> fishings = new ArrayList<Fishing>();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            fishings.add(Fishing.newFishingGround(dbObject));
        }
        return fishings;
    }
}
