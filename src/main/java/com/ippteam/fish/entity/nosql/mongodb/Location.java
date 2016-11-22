package com.ippteam.fish.entity.nosql.mongodb;

import org.springframework.data.geo.Point;

import java.util.List;

/**
 * Created by isunimp on 16/11/21.
 */
public class Location {
    String type;
    // 保存一个坐标点的两个参数（经度/纬度）
    List coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List coordinates) {
        this.coordinates = coordinates;
    }
}
