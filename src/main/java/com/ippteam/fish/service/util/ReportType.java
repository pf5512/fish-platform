package com.ippteam.fish.service.util;

import java.lang.annotation.*;

/**
 * Created by isunimp on 17/1/6.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ReportType {
    String value();
}
