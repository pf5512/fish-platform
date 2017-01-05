package com.ippteam.fish.service;

import com.ippteam.fish.dao.nosql.mongodb.ReportDao;
import com.ippteam.fish.entity.nosql.mongodb.Report;
import com.ippteam.fish.entity.nosql.mongodb.ReportType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by isunimp on 17/1/4.
 */
public abstract class BaseServiceImpl {
    @Autowired
    ReportDao reportDao;

    public void report(String id, String reason, List<String> evidences) throws Exception {
        ReportType reportType;
        String clazzName = this.getClass().getSimpleName();
        if (clazzName.equals("FishingServiceImpl")) {
            reportType = ReportType.FISHING;
        } else if (clazzName.equals("MomentServiceImpl")) {
            reportType = ReportType.MOMENT;
        } else if (clazzName.equals("UserServiceImpl")) {
            reportType = ReportType.USER;
        } else {
            throw new Exception("ReportType Exception");
        }

        Report report = new Report(id, reportType, reason, evidences);
        reportDao.insert(report);
    }
}
