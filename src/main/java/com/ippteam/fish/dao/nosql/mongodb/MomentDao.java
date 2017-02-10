package com.ippteam.fish.dao.nosql.mongodb;

import com.ippteam.fish.dao.nosql.mongodb.util.MongoBaseDao;
import com.ippteam.fish.entity.nosql.mongodb.Comment;
import com.ippteam.fish.entity.nosql.mongodb.Moment;
import com.ippteam.fish.util.JSON;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
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
            if (this.mongoTemplate.collectionExists(Moment.class)) {
                needCreateIndex = false;
            }
        }
        if (needCreateIndex) {
            createIndex("location", "2dsphere");
            needCreateIndex = false;
        }
        super.insert(moment);
    }

    public void banned(String id) {
        Moment moment = super.findById(id);
        if (moment == null) return;

        Query query = this.queryById(id);
        Update update = new Update();
        update.set("display", false);
        mongoTemplate.upsert(query, update, Moment.class);
    }

    public List<Moment> getMoments(final Double longitude, final Double latitude, Integer page) throws Exception {
        Query query = new Query().with(new Sort(new Sort.Order(Sort.Direction.DESC, "date"))).skip(page * 30).limit(30);
        return this.mongoTemplate.find(query, Moment.class);
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