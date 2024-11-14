package com.tencent.livelink.demo.model;

import lombok.Builder;
import lombok.Data;

// 生成用户登录态信息""""""
@Data
@Builder
public class LivelinkUserInfos {
    // 必填:平台用户的唯一标识符。
    private String userID;

    // 可选:标识用户是否为主播。默认为"0"（非主播），如果用户是主播，则设置为"1"。
    private String isAnchor;

    // 可选但建议填写:用户的真实IP地址，支持IPv4和IPv6格式。
    private String clientIP;
}
