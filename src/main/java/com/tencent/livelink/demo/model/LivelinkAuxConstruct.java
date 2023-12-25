package com.tencent.livelink.demo.model;

import com.alibaba.fastjson2.JSONObject;
import com.tencent.livelink.demo.util.JsonUtil;
import java.util.HashMap;

// Livelink的辅助构造，用于生成请求的body，或者生成code
public class LivelinkAuxConstruct {

    private LivelinkCallFlow livelinkCallFlow;
    private HashMap<String, Object> flowIdMap = new HashMap<>();
    private HashMap<String, Object> codeMap = new HashMap<>();

    public LivelinkAuxConstruct(LivelinkCallFlow livelinkCallFlow) {
        this.livelinkCallFlow = livelinkCallFlow;
        flowIdMap.put(
                "flowId",
                livelinkCallFlow.getFlowId()
        );
        codeMap.put(
                "userid",
                livelinkCallFlow.getUserId()
        );
    }


    public void addBodyParam(String key, Object value) {
        this.flowIdMap.put(key, value);
    }


    public JSONObject genBodyJson() {
        String jsonStr = JsonUtil.objectToJsonString(flowIdMap);
        return JsonUtil.parseJSONObject(jsonStr);
    }

    public void addCodeParam(String key, Object value) {
        this.codeMap.put(key, value);
    }

    public String genCodeJson() {
        return  JsonUtil.objectToJsonString(codeMap);
    }

}
