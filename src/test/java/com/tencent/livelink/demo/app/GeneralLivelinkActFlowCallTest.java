package com.tencent.livelink.demo.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tencent.livelink.demo.model.LivelinkCallFlow;
import com.tencent.livelink.demo.model.LivelinkKeyInfos;
import com.tencent.livelink.demo.util.JsonUtil;
import com.tencent.livelink.demo.util.LivelinkUtil;
import org.junit.jupiter.api.Test;

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

        // 解析返回的response，验证iRet为0表示成功
        Integer iRet = JsonUtil.parseJSONObject(response).getInteger("iRet");
        assertEquals(iRet, 0);


    }

}
