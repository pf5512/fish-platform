package com.ippteam.fish.util;

import org.jetbrains.annotations.Contract;

import java.util.List;

/**
 * Created by isunimp on 16/11/16.
 */
public class Fix {

    static public <E> E list(List<E> list, int index) {
        return list != null && list.size() > 0 && index >= 0 && list.size() > index ? list.get(index) : null;
    }

}
