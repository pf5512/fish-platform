package com.ippteam.fish.entity.nosql.mongodb;

import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isunimp on 16/11/21.
 */
public class Location {
    String type = "Point";
    // 保存一个坐标点的两个参数（经度/纬度）
    List coordinates;

    public Location() {

    }

    public Location(final double longitude, final double latitude, final String type) {
        this.type = type;
        if (coordinates == null) {
            coordinates = new ArrayList<Double>();
        }
        this.coordinates.add(longitude);
        this.coordinates.add(latitude);
    }

    public Location(final double longitude, final double latitude) {
        this.type = "Point";
        if (coordinates == null) {
            coordinates = new ArrayList<Double>();
        }
        this.coordinates.add(longitude);
        this.coordinates.add(latitude);
    }

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
