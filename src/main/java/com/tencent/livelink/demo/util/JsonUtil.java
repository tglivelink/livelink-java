package com.tencent.livelink.demo.util;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson2.JSON;

@Slf4j
public class JsonUtil {

    // 对象转换为json字符串
    public static <T> String objectToJsonString(T obj) {

        try {
            return JSON.toJSONString(obj);
        } catch (Exception e) {
            log.error(
                    "objectToJson  failed! obj clz:{} !",
                    obj.getClass().getName()
            );
            throw new RuntimeException("objectToJson  failed! obj clz:" + obj
                    .getClass()
                    .getName());
        }
    }

    // json字符串转化为JSONObject
    public static JSONObject parseJSONObject(String jsonStr) {

        if (isJson(jsonStr)) {
            return JSON.parseObject(jsonStr.trim());
        }

        return null;
    }

    // 判断字符串是否为json格式
    public static boolean isJson(String str) {
        try {

            if (str == null) {
                return false;
            }

            return JSON.isValid(str.trim());

        } catch (Exception e) {
            log.error(
                    "isJson e:",
                    e
            );
            return false;
        }
    }
}
