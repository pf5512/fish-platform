package com.ippteam.fish.entity.nosql.mongodb;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ippteam.fish.util.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.geo.Point;

import java.util.List;

/**
 * Created by isunimp on 16/11/21.
 */

@Document(collection = "fishing")
public class Fishing {

    @Id
    @JsonIgnore
    ObjectId _id;

    String summary;

    long adder;

    Location location;

    static public Fishing newFishingGround(DBObject dbObject) throws Exception {
        String string = JSON.stringify(dbObject);
        Fishing fishing = JSON.parse(string, Fishing.class);
        if (dbObject instanceof BasicDBObject) {
            BasicDBObject basicDBObject = (BasicDBObject) dbObject;
            Object idObject = basicDBObject.get("_id");
            if (idObject instanceof ObjectId) {
                fishing.set_id((ObjectId) idObject);
            }
        }
        return fishing;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public long getAdder() {
        return adder;
    }

    public void setAdder(long adder) {
        this.adder = adder;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}