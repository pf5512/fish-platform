package com.ippteam.fish.dao.nosql.mongodb;

import com.ippteam.fish.dao.nosql.mongodb.util.MongoBaseDao;
import com.ippteam.fish.entity.nosql.mongodb.Report;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by isunimp on 17/1/4.
 */

@Repository("ReportDao")
public class ReportDao extends MongoBaseDao<Report> {

    public void handled(String id) {
        Report report = super.findById(id);
        if (report == null) return;

        Query query = this.queryById(id);
        Update update = new Update();
        update.set("handled", true);
        mongoTemplate.upsert(query, update, Report.class);
    }
}
