package com.tencent.livelink.demo.model;

import com.alibaba.fastjson2.JSONObject;
import com.tencent.livelink.demo.util.JsonUtil;
import java.util.HashMap;

// Livelink的辅助构造，用于生成请求的body，或者生成code
public class LivelinkAuxConstruct {
    private HashMap<String, Object> bodyMap;
    private HashMap<String, Object> codeMap = new HashMap<>();

    public LivelinkAuxConstruct(LivelinkUserInfos livelinkUserInfos, HashMap<String, Object> bodyParams) {
        bodyMap = bodyParams;
        codeMap.put(
                "userid",
                livelinkUserInfos.getUserID()
        );
        if (livelinkUserInfos.getIsAnchor() != null && !livelinkUserInfos.getIsAnchor().equals("")) {
            codeMap.put(
                    "isAnchor",
                    livelinkUserInfos.getIsAnchor()
            );
        }
        if (livelinkUserInfos.getClientIP() != null && !livelinkUserInfos.getClientIP().equals("")) {
            codeMap.put(
                    "clientIP",
                    livelinkUserInfos.getClientIP()
            );
        }
    }

    public JSONObject genBodyJson() {
        String jsonStr = JsonUtil.objectToJsonString(bodyMap);
        return JsonUtil.parseJSONObject(jsonStr);
    }

    public String genCodeJson() {
        return  JsonUtil.objectToJsonString(codeMap);
    }

}
