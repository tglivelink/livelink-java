package com.tencent.livelink.demo.app;

import com.tencent.livelink.demo.mode.LivelinkCallFlow;
import com.tencent.livelink.demo.mode.LivelinkKeyInfos;
import com.tencent.livelink.demo.util.LivelinkUtil;

public class LivelinkCallActAndFlow {

    public static void main(String[] args) throws Exception {

        // 从livelink申请的appId、secKey和signKey（建议调用方使用中心化配置）
        LivelinkKeyInfos livelinkKeyInfos = LivelinkKeyInfos.builder()
                .appId("联系livelink同学")
                .secKey("联系livelink同学")
                .signKey("联系livelink同学")
                .build();

        // 构建活动的基本信息
        LivelinkCallFlow livelinkCallFlow = LivelinkCallFlow.builder()
                .actId("5940")
                .gameId("yxzj")
                .apiName("GetBindInfo")
                .flowId("d9a497e8")
                .userId("123456")
                .build();

        // 生成和发起请求
        final long start = System.currentTimeMillis();
        // 发起调用（包含签名）
        String response = LivelinkUtil.callActWithFlow(
                livelinkKeyInfos,
                livelinkCallFlow,
               true
        );

        final long end = System.currentTimeMillis();
        System.out.println("Response Result:" + response + ", cost:" + (end - start));

    }
}
