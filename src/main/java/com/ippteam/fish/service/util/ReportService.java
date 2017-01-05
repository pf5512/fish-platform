package com.ippteam.fish.service.util;

import java.util.List;

/**
 * Created by isunimp on 17/1/5.
 */
public interface ReportService {

    /**
     *  举报资源
     * @param id        资源id
     * @param reason    举报理由
     * @param evidences 证据
     * @throws Exception
     */
    void report(String id, String reason, List<String> evidences) throws Exception;

    /**
     *  封禁资源
     * @param id 资源id
     */
    void banned(String id);
}
