#### 背景
- Java调用livelink的运营活动接口的demo
- 只提供参考，请相关同学做好异常处理
- 目前Livelink涉及到多套签名，请参考test的各种测试Example

#### 模块介绍
```shell
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── tencent
    │   │           └── livelink
    │   │               └── demo
    │   │                   ├── app
    │   │                   ├── constant
    │   │                   ├── encrypt
    │   │                   ├── model
    │   │                   └── util
    │   └── resources
    └── test
        └── java
            └── com
                └── tencent
                    └── livelink
                        └── demo
                            ├── app
                            └── util
```

- app：LivelinkCallActAndFlow 调用的入口，详细参考test的app演示demo
- constant：基本的常量，包括测试和正式的host信息
- encrypt：加密使用，主要是生成code的AESPLain和生成sign的MD5Util
- model: 比较重要，用于封装基本model
  - LivelinkKeyInfos 基本的秘钥信息，请联系和livelink对接的开发同学，包括：平台ID（appId）、生成code的key和生成sign的key
  - LivelinkCallFlow 基本的调用信息，包括活动id、flowid、apiName和userid等信息
  - LivelinkAuxConstruct 辅助工具model，用于补充额外参数
- util：基本的工具模块
  - HttpUtil：封装的请求
  - JsonUtil：封装的json工具
  - LivelinkUtil：核心的livelink工具


#### 使用代码如下
- 下面演示在XXX平台调用YYY的活动（livelink配置的活动id：5940），apiName是GetBindInfo，flowId是d9a497e8，用户信息是 6655232 
```java
// 从livelink申请的appId、secKey和signKey（建议调用方使用中心化配置）
LivelinkKeyInfos livelinkKeyInfos = LivelinkKeyInfos.builder()
        .appId("联系livelink同学")
        .secKey("联系livelink同学")
        .signKey("联系livelink同学")
        .build();

        // 构建活动的基本信息
LivelinkCallFlow livelinkCallFlow = LivelinkCallFlow.builder()
        .actId("5940")
        .gameId("YYY")
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

```