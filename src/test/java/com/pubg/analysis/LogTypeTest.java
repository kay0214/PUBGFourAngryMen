/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pubg.analysis.utils.DateUtil;
import com.pubg.analysis.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sunpeikai
 * @version LogTypeTest, v0.1 2020/7/14 9:14
 * @description
 */
@Slf4j
public class LogTypeTest {

    private static final String fileFullPath = "C:\\Users\\dell\\Desktop\\8a8f1587-c064-11ea-a4fb-164be6f57315-telemetry.json";

    /*
        LogPlayerLogout、LogArmorDestroy、LogMatchEnd、LogPlayerKill、LogItemUnequip、LogItemEquip、LogItemPickup
        LogPhaseChange、LogPlayerCreate、LogVaultStart、LogItemDetach、LogPlayerRevive、LogPlayerAttack、LogPlayerTakeDamage
        LogPlayerMakeGroggy、LogWeaponFireCount、LogItemUse、LogVehicleRide、LogItemPickupFromCarepackage、LogObjectDestroy
        LogCarePackageLand、LogMatchStart、LogParachuteLanding、LogVehicleLeave、LogSwimStart、LogSwimEnd、LogItemDrop
        LogMatchDefinition、LogPlayerLogin、LogObjectInteraction、LogItemPickupFromLootBox、LogItemAttach、LogCarePackageSpawn

        LogGameStatePeriodic、LogVehicleDestroy、LogPlayerPosition、LogHeal、LogPlayerUseThrowable
     */
    public static void main(String[] args){
        // logType();
        Date date = DateUtil.getFromStr("2020-07-12T14:56:52Z");
        log.info("year[{}],month[{}],day[{}],hour[{}],min[{}],sec[{}]",date.getYear(),date.getMonth(),date.getDay(),date.getHours(),date.getMinutes(),date.getSeconds());
    }

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
