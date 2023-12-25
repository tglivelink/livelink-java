package com.tencent.livelink.demo.model;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

// 表示用户调用livelink的不同活动的接口和flow
@Data
@Builder
public class LivelinkCallFlow {

    private String gameId;

    private String actId;

    private String flowId;

    private String apiName;

    private String userId;

    @Default
    private Double version = 2.0;

}
