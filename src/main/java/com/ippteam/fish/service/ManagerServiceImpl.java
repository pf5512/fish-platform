package com.ippteam.fish.service;

import com.ippteam.fish.dao.nosql.mongodb.ReportDao;
import com.ippteam.fish.entity.nosql.mongodb.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by isunimp on 17/1/6.
 */
@Service("ManagerService")
public class ManagerServiceImpl {

    @Autowired
    ReportDao reportDao;
    @Autowired
    FishingService fishingService;
    @Autowired
    MomentServiceImpl momentService;
    @Autowired
    UserService userService;

    public List<Report> reports() {
        return reportDao.findAll();
    }

    public Report reportById(String id) {
        return reportDao.findById(id);
    }

    public void handled(String id, boolean banned) throws Exception {
        Report report = this.reportById(id);
        String toId = report.getToId();
        String type = report.getType();
        if (banned) {
            if (type.equals("FISHING")) {
                fishingService.banned(id, toId);
            } else if (type.equals("MOMENT")) {
                momentService.banned(id, toId);
            } else if (type.equals("USER")) {
                userService.banned(id, toId);
            }
        } else {
            reportDao.handled(id);
        }
    }
}