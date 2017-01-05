package com.ippteam.fish.entity.nosql.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by isunimp on 17/1/4.
 */
@Document(collection = "report")
public class Report {

    @Id
    String _id;

    // 被举报的id
    String toId;

    // 举报的类型
    ReportType type;

    // 举报原因
    String reason;

    // 证据截图
    List<String> evidences;

    public Report(String id, ReportType type, String reason, List<String> evidences) {
        this.setToId(id);
        this.setType(type);
        this.setReason(reason);
        this.setEvidences(evidences);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

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
