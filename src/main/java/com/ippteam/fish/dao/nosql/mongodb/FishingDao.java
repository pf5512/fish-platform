package com.ippteam.fish.dao.nosql.mongodb;

import com.ippteam.fish.dao.nosql.mongodb.util.MongoBaseDao;
import com.ippteam.fish.entity.nosql.mongodb.Fishing;
import com.ippteam.fish.util.JSON;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by isunimp on 16/11/22.
 */

@Repository("FishingDao")
public class FishingDao extends MongoBaseDao<Fishing> {

    public void insert(Fishing fishing) {
        DBCollection collection = null;
        if (!mongoTemplate.collectionExists(Fishing.class)) {
            mongoTemplate.createCollection(Fishing.class);
            String collectionName = mongoTemplate.getCollectionName(Fishing.class);
            collection = mongoTemplate.getCollection(collectionName);
            if (collection != null) {
                collection.createIndex(new BasicDBObject("location", "2dsphere"));
            }
        }
        super.insert(fishing);
    }

    public void update(Fishing fishing) throws Exception {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(fishing.get_id())));
        DBObject dbObject = BasicDBObjectBuilder.start()
                .add("summary", fishing.getSummary())
                .add("adder", fishing.getAdder())
                .add("location", fishing.getLocation()).get();

        Update update = new Update();
        update.set("summary", dbObject);
        mongoTemplate.updateFirst(query, update, Fishing.class);
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

        BasicDBObject location = new BasicDBObject();
        location.put("$near", near);

        BasicDBObject conditionBbject = new BasicDBObject("location", location);

        DBCursor cursor = collection.find(conditionBbject);
        List<Fishing> fishings = new ArrayList<Fishing>();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            Fishing fishing = JSON.parse(dbObject, Fishing.class);
            fishings.add(fishing);
        }
        return fishings;
    }
}
