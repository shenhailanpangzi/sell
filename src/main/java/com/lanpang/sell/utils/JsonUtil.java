package com.lanpang.sell.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**json格式化
 * Created by 杨浩
 * 2017-07-04 01:30
 */
public class JsonUtil {

    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
