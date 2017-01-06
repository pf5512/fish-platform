package com.ippteam.fish.service.util;

import com.ippteam.fish.entity.nosql.mongodb.Report;

import java.util.List;

/**
 * Created by isunimp on 17/1/5.
 */
public interface ReportService {

    /**
     * 举报资源
     *
     * @param report 举报信息
     * @throws Exception
     */
    void report(Report report) throws Exception;

    /**
     * 封禁资源
     *
     * @param id   举报记录的id
     * @param toId 被封禁资源的id
     */
    void banned(String id, String toId) throws Exception;

    /**
     * 封禁资源
     *
     * @param toId 被封禁资源的id
     */
    void banned(String toId);
}
