/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pubg.analysis.entity.log.BaseLog;
import com.pubg.analysis.utils.DateUtil;
import com.pubg.analysis.utils.FileUtil;
import com.pubg.analysis.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sunpeikai
 * @version LogTypeTest, v0.1 2020/7/14 9:14
 * @description
 */
@Slf4j
public class LogTypeTest {

    private static final String fileFullPath = "C:\\Users\\dell\\Desktop\\8a8f1587-c064-11ea-a4fb-164be6f57315-telemetry.json";


    public static void main(String[] args) throws ParseException {
        // logType();
        logParse();
//        String dateStr = "2020-07-09T14:39:17.2783304Z";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
//        Date date = simpleDateFormat.parse(dateStr);
//        log.info(date);
    }

    public static void logParse(){
        String url = "https://telemetry-cdn.playbattlegrounds.com/bluehole-pubg/steam/2020/07/09/15/04/78fe2b5a-c1f5-11ea-a176-ea864576b2b5-telemetry.json";
        String result = HttpUtil.sendGetGZIP(url);
/*        FileUtil.fileWriteBuffer("C:\\Users\\dell\\Desktop\\received-78fe2b5a-c1f5-11ea-a176-ea864576b2b5-telemetry.json",result);
        JSONArray array = JSONObject.parseArray(result);
        int size = array.size();
        log.info("received size[{}]",size);
        for (int i=0 ; i<size ; i++){
            JSONObject json = array.getJSONObject(i);
            log.info("接收到的数据:{}", json.toJSONString());
        }*/
/*        List<BaseLog> baseLogs = JSONObject.parseArray(result,BaseLog.class);
        baseLogs.forEach(baseLog -> {
            log.info("接收到的数据:{}", JSON.toJSONString(baseLog));
        });*/
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'", Locale.US);
        JSONArray array = JSONObject.parseArray(result);
        List<BaseLog> baseLogs = array
                .parallelStream()
                .map(e -> {
                    JSONObject json = (JSONObject) e;
                    return json.toJavaObject(BaseLog.class);
                })
                .collect(Collectors.toList());

        baseLogs.forEach(baseLog -> {
            log.info("接收到的数据:{}", JSON.toJSONString(baseLog));
            // Date date = baseLog.get_D();
            // log.info("接收到的时[{}]分[{}]秒[{}]",date.getHours(),date.getMinutes(),date.getSeconds());
            // LocalDateTime date = baseLog.get_D();
            // log.info("接收到的时[{}]分[{}]秒[{}]",date.getHour(),date.getMinute(),date.getSecond());
        });
        log.info("received size[{}]",baseLogs.size());
    }

    /*
        LogPlayerLogout、LogArmorDestroy、LogMatchEnd、LogPlayerKill、LogItemUnequip、LogItemEquip、LogItemPickup
        LogPhaseChange、LogPlayerCreate、LogVaultStart、LogItemDetach、LogPlayerRevive、LogPlayerAttack、LogPlayerTakeDamage
        LogPlayerMakeGroggy、LogWeaponFireCount、LogItemUse、LogVehicleRide、LogItemPickupFromCarepackage、LogObjectDestroy
        LogCarePackageLand、LogMatchStart、LogParachuteLanding、LogVehicleLeave、LogSwimStart、LogSwimEnd、LogItemDrop
        LogMatchDefinition、LogPlayerLogin、LogObjectInteraction、LogItemPickupFromLootBox、LogItemAttach、LogCarePackageSpawn
        LogGameStatePeriodic、LogVehicleDestroy、LogPlayerPosition、LogHeal、LogPlayerUseThrowable
    */
    public static void logType(){
        Set<String> logType = new HashSet<>();
        long startTime = System.currentTimeMillis();
        String data = FileUtil.fileReadBuffer(fileFullPath);
        log.info("read file time consume[{}]",(System.currentTimeMillis() - startTime) + "ms");
        startTime = System.currentTimeMillis();
        if(!StringUtils.isEmpty(data)){
            JSONArray array = JSONObject.parseArray(data);
            int arraySize = array.size();
            log.info("parse array time consume[{}] and array size[{}]",(System.currentTimeMillis() - startTime) + "ms", arraySize);
            startTime = System.currentTimeMillis();
            for(int i=0; i<arraySize; i++){
                JSONObject json = array.getJSONObject(i);
                logType.add(json.getString("_T"));
            }
            log.info("handle result time consume[{}]",(System.currentTimeMillis() - startTime) + "ms");
            logType.forEach(System.out::println);
        }
    }
}
