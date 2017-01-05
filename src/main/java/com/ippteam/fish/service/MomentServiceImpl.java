package com.ippteam.fish.service;

import com.ippteam.fish.dao.nosql.mongodb.MomentDao;
import com.ippteam.fish.entity.nosql.mongodb.Comment;
import com.ippteam.fish.entity.nosql.mongodb.Moment;
import com.ippteam.fish.service.util.ReportService;
import com.ippteam.fish.service.util.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by isunimp on 16/12/6.
 */
@Service("MomentService")
public class MomentServiceImpl extends ReportServiceImpl implements ReportService {

    @Autowired
    MomentDao momentDao;

    public void addMoment(Moment moment) {
        momentDao.insert(moment);
    }

    public int like(String id) {
        return momentDao.addLike(id);
    }

    public List<Comment> addComment(String id, Comment comment) {
        return momentDao.addComment(id, comment);
    }

    public Moment getMoment(String id) {
        return momentDao.findById(id);
    }

    public List<Moment> getMoments(final double longitude, final double latitude) throws Exception {
        return momentDao.getMoments(longitude, latitude);
    }

    public void banned(String id) {
        momentDao.banned(id);
    }
}