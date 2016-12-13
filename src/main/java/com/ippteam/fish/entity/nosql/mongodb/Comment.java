package com.ippteam.fish.entity.nosql.mongodb;

import java.util.Date;

/**
 * Created by isunimp on 16/12/5.
 */
public class Comment {

    // 评论内容
    String content;

    // 评论人
    String from;

    // 被评论的 Moment id
    String to;

    // 评论时间
    Date date;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}