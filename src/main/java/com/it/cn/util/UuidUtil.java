package com.it.cn.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UuidUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static Date toDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = sdf.parse(date);
        return dates;
    }
}
