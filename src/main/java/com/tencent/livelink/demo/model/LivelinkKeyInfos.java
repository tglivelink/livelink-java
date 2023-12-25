package com.tencent.livelink.demo.model;

import lombok.Builder;
import lombok.Data;

// 表示平台从livelink获取的基本的key信息（建议中心化配置）
@Data
@Builder
public class LivelinkKeyInfos {

    // 生成code的key
    private String secKey;

    // 生成sign的key
    private String signKey;

    // appId也是平台的id信息
    private String appId;

}
