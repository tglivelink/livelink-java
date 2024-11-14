package com.tencent.livelink.demo.util;

import static com.tencent.livelink.demo.constant.CommonConstant.LIVELINK_PROD_URL;
import static com.tencent.livelink.demo.constant.CommonConstant.LIVELINK_TEST_URL;

import com.alibaba.fastjson2.JSONObject;
import com.tencent.livelink.demo.constant.CommonConstant;
import com.tencent.livelink.demo.encrypt.AESPlain;
import com.tencent.livelink.demo.encrypt.MD5Util;
import com.tencent.livelink.demo.model.LivelinkAuxConstruct;
import com.tencent.livelink.demo.model.LivelinkKeyInfos;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.tencent.livelink.demo.model.LivelinkUserInfos;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class LivelinkUtil {

    /**
     * 生成校验的code
     *
     * @param livelinkKeyInfos livelink分配的基本key信息
     * @param livelinkUserInfos livelink的用户信息信息
     * @param bodyParams 请求带的http body
     * @return 生成校验的code
     * @throws Exception 异常，注意异常处理
     */
    public static String genCode(
            LivelinkKeyInfos livelinkKeyInfos,
            LivelinkUserInfos livelinkUserInfos,
            HashMap<String, Object> bodyParams
    ) throws Exception {

        LivelinkAuxConstruct livelinkAuxConstruct = new LivelinkAuxConstruct(livelinkUserInfos, bodyParams);
        return AESPlain.encryptJson(
                livelinkAuxConstruct.genCodeJson(),
                livelinkKeyInfos.getSecKey()
        );
    }

    /**
     * sign生成
     *
     * @param livelinkKeyInfos livelink分配的基本key信息
     * @param queryParams url的query参数
     * @param code 加密后的游戏五元组的code
     * @param tm 秒级时间戳
     * @param nonce 随机8位数字字符串
     * @return 生成sign
     * @throws Exception 异常，注意异常处理
     */
    public static String genSign(
            LivelinkKeyInfos livelinkKeyInfos,
            HashMap<String, Object> queryParams,
            String code,
            long tm,
            String nonce
    ) throws Exception {

        Map<String, Object> tmp = new HashMap<>();
        tmp.put("livePlatId", livelinkKeyInfos.getAppId());
        tmp.put("t", tm);
        tmp.put("nonce", nonce);
        tmp.put("code", URLEncoder.encode(code, "utf-8"));

        // 不参能再增加的字段
        Map<String, Object> blacklistFields= new HashMap<>();
        blacklistFields.put("c", 0);
        blacklistFields.put("apiName", 0);
        blacklistFields.put("sig", 0);
        blacklistFields.put("fromGame", 0);
        blacklistFields.put("backUrl", 0);
        blacklistFields.put("a", 0);
        // 这几个字段上面已经添加不能重复加
        blacklistFields.put("livePlatId", 0);
        blacklistFields.put("t", 0);
        blacklistFields.put("nonce", 0);
        blacklistFields.put("code", 0);

        // 然后加入用户传入的参数
        for (Entry<String, Object> entry : queryParams.entrySet()) {
            if (entry.getKey() == null || blacklistFields.containsKey(entry.getKey())) {
                continue;
            }
            tmp.put(entry.getKey(), entry.getValue());
        }

        tmp = sortMapByKey(tmp, false);
        List<Object> data = new ArrayList<>();
        for (Map.Entry<String, Object> entry : tmp.entrySet()) {
            data.add(entry.getValue());
        }

        data.add(livelinkKeyInfos.getSignKey());
        String finalData = StringUtils.join(data, "+");
        String md5 = MD5Util.encryptByMD5(finalData);
        if (null == md5) {
            md5 = CommonConstant.DEFAULT_EMPTY_STR;
        }
        return md5.toLowerCase();
    }

    /**
     * 对map进行按key排序
     *
     * @param map 原始输入map
     * @param isDesc 是否为降序
     * @return 按key排序后的map
     */
    public static Map<String, Object> sortMapByKey(
            Map<String, Object> map,
            boolean isDesc
    ) {
        List<Entry<String, Object>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> {
            int compare = (o1.getKey()).compareTo(o2.getKey());

            if (isDesc) {
                return -compare;
            }

            return compare;
        });

        Map<String, Object> returnMap = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : list) {
            returnMap.put(
                    entry.getKey(),
                    entry.getValue()
            );
        }
        return returnMap;
    }


    /**
     * 生成指定范围的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    public static String getRandom(
            int min,
            int max
    ) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return String.valueOf(s);
    }


    /**
     * 生成接口调用
     *
     * @param livelinkKeyInfos livelink分配的基本key信息
     * @param livelinkUserInfos livelink的用户信息
     * @param queryParams url的query参数
     * @param bodyParams http请求的body参数
     * @param isTest 是否为测试
     * @throws Exception 异常，注意异常处理
     */
    public static String callActWithFlow (
            LivelinkKeyInfos livelinkKeyInfos,
            LivelinkUserInfos livelinkUserInfos,
            HashMap<String, Object> queryParams,
            HashMap<String, Object> bodyParams,
            boolean isTest
    ) throws Exception {

        // 时间戳和盐值
        long tm = System.currentTimeMillis() / 1000;
        String nonce = getRandom(
                100000,
                999999
        );

        // 生成code
        String code = genCode(
                livelinkKeyInfos,
                livelinkUserInfos,
                bodyParams
        );

        // 生成sign
        String sign = genSign(
                livelinkKeyInfos,
                queryParams,
                code,
                tm,
                nonce
        );

        // 生成调用的url
        String url = buildUrl(
                isTest,
                livelinkKeyInfos,
                queryParams,
                nonce,
                code,
                sign,
                tm
        );

        LivelinkAuxConstruct livelinkAuxConstruct = new LivelinkAuxConstruct(livelinkUserInfos, bodyParams);
        JSONObject bodyJson = livelinkAuxConstruct.genBodyJson();
        log.info("livelink call url: {}, body: {}", url, bodyJson);

        // 可以添加其他可选参数
        // livelinkAuxConstruct.addBodyParam("isAnchor", 0);
        return HttpUtil.sendPost(
                url,
                bodyJson,
                new HashMap<>(),
                3000,
                5000
        );

    }

    /**
     * 生成最终调用的URL
     *
     * @param isTest 是否测试
     * @param livelinkKeyInfos livelink分配的基本key信息
     * @param queryParams url的query参数
     * @param nonce 盐值
     * @param code 用户code
     * @param sign 签名
     * @param tm 时间戳
     * @return url
     * @throws Exception 异常处理
     */
    public static String buildUrl(
            boolean isTest,
            LivelinkKeyInfos livelinkKeyInfos,
            HashMap<String, Object> queryParams,
            String nonce,
            String code,
            String sign,
            long tm
    ) throws Exception {
        StringBuilder builder;
        if (isTest) {
            builder = new StringBuilder(LIVELINK_TEST_URL);
        } else {
            builder = new StringBuilder(LIVELINK_PROD_URL);
        }
        builder.append("livePlatId=")
                .append(livelinkKeyInfos.getAppId());

        for (Entry<String, Object> entry : queryParams.entrySet()) {
            builder.append("&")
                    .append(entry.getKey())
                    .append("=")
                    .append(entry.getValue());
        }

        builder.append("&t=")
                .append(tm)
                .append("&nonce=")
                .append(nonce)
                .append("&code=")
                .append(URLEncoder.encode(code, "utf-8"))
                .append("&sig=")
                .append(sign);
        return builder.toString();
    }
}

