package com.ippteam.fish.pojo;

import java.util.List;

/**
 * Created by isunimp on 17/1/20.
 */
public class Report {

    // 举报原因
    String reason;

    // 证据截图
    List<String> evidences;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getEvidences() {
        return evidences;
    }

    public void setEvidences(List<String> evidences) {
        this.evidences = evidences;
    }
}
