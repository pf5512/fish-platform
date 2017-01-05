package com.ippteam.fish.dao.nosql.mongodb;

import com.ippteam.fish.dao.nosql.mongodb.util.MongoBaseDao;
import com.ippteam.fish.entity.nosql.mongodb.Report;
import org.springframework.stereotype.Repository;

/**
 * Created by isunimp on 17/1/4.
 */

@Repository("ReportDao")
public class ReportDao extends MongoBaseDao<Report> {

}
