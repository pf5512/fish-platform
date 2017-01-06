package com.ippteam.fish.service.util;

import com.ippteam.fish.dao.nosql.mongodb.ReportDao;
import com.ippteam.fish.entity.nosql.mongodb.Report;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by isunimp on 17/1/4.
 */
public abstract class ReportServiceImpl implements ReportService {
    @Autowired
    ReportDao reportDao;

    public void report(Report report) throws Exception {
        String reportType;

        Class clazz = this.getClass();
        if (!clazz.isAnnotationPresent(ReportType.class)) {
            throw new Exception(String.format("%s is no use annotations 'ReportType'.", clazz.getName()));
        }
        ReportType annotation = (ReportType) clazz.getAnnotation(ReportType.class);
        reportType = annotation.value();
        report.setType(reportType);
        reportDao.insert(report);
    }

    public void banned(String id, String toId) throws Exception {
        Class clazz = this.getClass();
        try {
            Method method = clazz.getMethod("banned", String.class);
            method.invoke(this, toId);
            reportDao.handled(id);
        } catch (NoSuchMethodException e) {

        } catch (IllegalAccessException e) {

        } catch (IllegalArgumentException e) {

        } catch (InvocationTargetException e) {

        }
    }
}
