package com.ippteam.fish.service;

import com.ippteam.fish.dao.nosql.mongodb.MomentDao;
import com.ippteam.fish.entity.nosql.mongodb.Comment;
import com.ippteam.fish.entity.nosql.mongodb.Moment;
import com.ippteam.fish.service.util.ReportService;
import com.ippteam.fish.service.util.ReportServiceImpl;
import com.ippteam.fish.service.util.ReportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by isunimp on 16/12/6.
 */
@ReportType("MOMENT")
@Service("MomentService")
public class MomentServiceImpl extends ReportServiceImpl implements ReportService {

    @Autowired
    MomentDao momentDao;

    public void add(Moment moment) {
        momentDao.insert(moment);
    }

    public Moment moment(String id) {
        return momentDao.findById(id);
    }

    public List<Moment> moments(final Double longitude, final Double latitude, Integer page) throws Exception {
        return momentDao.getMoments(longitude, latitude, page);
    }

    public int like(String id) {
        return momentDao.addLike(id);
    }

    public List<Comment> addComment(String momentId, Comment comment) {
        return momentDao.addComment(momentId, comment);
    }

    public void banned(String id) {
        momentDao.banned(id);
    }
}