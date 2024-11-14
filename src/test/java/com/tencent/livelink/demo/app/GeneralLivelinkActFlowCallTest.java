package com.tencent.livelink.demo.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tencent.livelink.demo.model.LivelinkKeyInfos;
import com.tencent.livelink.demo.model.LivelinkUserInfos;
import com.tencent.livelink.demo.util.JsonUtil;
import com.tencent.livelink.demo.util.LivelinkUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

// livelink运营活动通用流程调用测试
public class GeneralLivelinkActFlowCallTest {

    @Test
    void testActFlowCall() throws Exception {

        // 从livelink申请的appId、secKey和signKey（建议调用方使用中心化配置）
        LivelinkKeyInfos livelinkKeyInfos = LivelinkKeyInfos.builder()
                .appId("")
                .secKey("")
                .signKey("")
                .build();

        // 用于构造用户的登录态信息
        LivelinkUserInfos livelinkUserInfos = LivelinkUserInfos.builder().
                userID("123456").build();

        // 构建query参数
        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("actId", "9639");
        queryParams.put("gameId", "val");
        queryParams.put("apiName","ApiRequest");
        queryParams.put("userId","123456");

        // 构建body参数（JSON格式）
        HashMap<String, Object> bodyParams = new HashMap<>();
        bodyParams.put("flowId", "e1df454d");

        // 生成和发起请求
        final long start = System.currentTimeMillis();
        // 发起调用（包含签名）
        String response = LivelinkUtil.callActWithFlow(
                livelinkKeyInfos,
                livelinkUserInfos,
                queryParams,
                bodyParams,
                true
        );

        final long end = System.currentTimeMillis();
        System.out.println("Response Result:" + response + ", cost:" + (end - start));

        // 解析返回的response，验证iRet为0表示成功
        Integer iRet = JsonUtil.parseJSONObject(response).getInteger("iRet");
        assertEquals(iRet, 0);


    }

}
