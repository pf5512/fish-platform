package com.ippteam.fish.service;

import java.util.List;

/**
 * Created by isunimp on 17/1/5.
 */
public interface BaseService {

    void report(String id, String reason, List<String> evidences) throws Exception;
}
