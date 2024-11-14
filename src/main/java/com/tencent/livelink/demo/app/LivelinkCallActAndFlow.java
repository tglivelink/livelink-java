package com.tencent.livelink.demo.app;

import com.tencent.livelink.demo.model.LivelinkKeyInfos;
import com.tencent.livelink.demo.model.LivelinkUserInfos;
import com.tencent.livelink.demo.util.LivelinkUtil;

import java.util.HashMap;

public class LivelinkCallActAndFlow {

    public static void main(String[] args) throws Exception {

        // 从livelink申请的appId、secKey和signKey（建议调用方使用中心化配置）
        LivelinkKeyInfos livelinkKeyInfos = LivelinkKeyInfos.builder()
                .appId("联系livelink同学")
                .secKey("联系livelink同学")
                .signKey("联系livelink同学")
                .build();
        // 用于构造用户的登录态信息
        LivelinkUserInfos livelinkUserInfos = LivelinkUserInfos.builder().
                userID("123456").build();

        // 构建query参数
        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("actId", "9639");
        queryParams.put("gameId", "val");
        queryParams.put("apiName","ApiRequest");

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

    }
}
