package com.ippteam.fish.dao.nosql.mongodb;

import com.ippteam.fish.dao.nosql.mongodb.util.CustomMongoIdSerializer;
import com.ippteam.fish.util.JSON;
import org.bson.types.ObjectId;

/**
 * Created by isunimp on 16/11/25.
 */
public class MongoBaseDao {

    public MongoBaseDao() {
        JSON.addSerializer(ObjectId.class, new CustomMongoIdSerializer());
    }
}
