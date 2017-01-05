package com.ippteam.fish.service;

import com.ippteam.fish.dao.nosql.mongodb.MomentDao;
import com.ippteam.fish.entity.nosql.mongodb.Comment;
import com.ippteam.fish.entity.nosql.mongodb.Moment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by isunimp on 16/12/6.
 */
@Service("MomentService")
public class MomentServiceImpl extends BaseServiceImpl implements BaseService {

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

    public Moment getMoment(String id)   {
        return momentDao.findById(id);
    }

    public List<Moment> getMoments(final double longitude, final double latitude) throws Exception {
        return momentDao.getMoments(longitude, latitude);
    }

    public void banned(String id){
        momentDao.banned(id);
    }
}