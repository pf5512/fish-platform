package com.ippteam.fish.dao.nosql.mongodb;

import com.ippteam.fish.dao.nosql.mongodb.util.MongoBaseDao;
import com.ippteam.fish.entity.nosql.mongodb.Comment;
import com.ippteam.fish.entity.nosql.mongodb.Moment;
import com.ippteam.fish.util.JSON;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isunimp on 16/12/5.
 */
@Repository("MomentDao")
public class MomentDao extends MongoBaseDao<Moment> {

    boolean needCreateIndex = true;

    public void insert(Moment moment) {
        if (needCreateIndex) {
            createIndex("location", "2dsphere");
            needCreateIndex = false;
        }
        super.insert(moment);
    }

    public List<Moment> getMoments(final double longitude, final double latitude) throws Exception {
        String command = String.format("{geoNear:\"moment\",near:[%f,%f],spherical:true,distanceMultiplier: 3963.2}", longitude, latitude);
        CommandResult commandResult = mongoTemplate.executeCommand(command);
        List<Moment> moments = new ArrayList<Moment>();
        if (commandResult.ok()) {
            Object results = commandResult.get("results");
            if (results instanceof BasicDBList) {
                BasicDBList resultList = (BasicDBList) results;
                for (Object obj : resultList) {
                    if (obj instanceof BasicDBObject) {
                        BasicDBObject basicDBObject = (BasicDBObject) obj;
                        Moment moment = JSON.parse(basicDBObject.get("obj"), Moment.class);
                        moment.setDistance((Double) basicDBObject.get("dis"));
                        moments.add(moment);
                    }
                }
            }
        }
        return moments;
    }

    public int addLike(String id) {
        Query query = this.queryById(id);
        Update update = new Update();
        update.inc("like", 1);
        mongoTemplate.upsert(query, update, Moment.class);

        Moment moment = mongoTemplate.findOne(query, Moment.class);
        return moment.getLike();
    }

    public List<Comment> addComment(String id, Comment comment) {
        Query query = this.queryById(id);
        Update update = new Update();
        update.addToSet("comments", comment);

        this.mongoTemplate.upsert(query, update, Moment.class);
        return this.mongoTemplate.findOne(query, Moment.class).getComments();
    }
}