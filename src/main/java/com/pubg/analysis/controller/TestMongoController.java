/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.controller;

import com.alibaba.fastjson.JSONObject;
import com.pubg.analysis.api.PubgApi;
import com.pubg.analysis.api.enums.PubgApiEnum;
import com.pubg.analysis.base.Page;
import com.pubg.analysis.entity.MongoTest;
import com.pubg.analysis.request.MongoTestRequest;
import com.pubg.analysis.response.MongoTestResponse;
import com.pubg.analysis.service.MongoTestDao;
import com.pubg.analysis.utils.DateUtil;
import com.pubg.analysis.utils.EntityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sunpeikai
 * @version TestMongo, v0.1 2020/7/10 10:49
 * @description
 */
@Api(value = "mongo测试类",tags = "mongo测试类")
@RestController
@RequestMapping("/test")
public class TestMongoController {

    @Autowired
    private MongoTestDao mongoTestDao;

    @ApiOperation(value = "测试单条插入",notes = "测试单条插入")
    @PostMapping("/insert")
    public String insert(@RequestBody MongoTestRequest request){
        MongoTest mongoTest = EntityUtil.copyBean(request,MongoTest.class);
        mongoTest.setCreateTime(new Date());
        mongoTestDao.insert(mongoTest);
        return "Ok";
    }

    @ApiOperation(value = "测试批量插入",notes = "测试批量插入")
    @PostMapping("/insertBatch")
    public String insertBatch(@RequestBody MongoTestRequest request){
        List<MongoTest> mongoTests = new ArrayList<>();
        for(int i=1;i<=10;i++){
            MongoTest mongoTest = EntityUtil.copyBean(request,MongoTest.class);
            mongoTest.setClassId(i);
            mongoTest.setHeadmaster(i==1?1:0);
            mongoTest.setCreateTime(new Date());
            mongoTests.add(mongoTest);
        }
        mongoTestDao.insertAll(mongoTests);
        return "Ok";
    }

    @ApiOperation(value = "测试分页查询",notes = "测试分页查询")
    @PostMapping("/searchPage")
    public Page<MongoTestResponse> searchPage(@RequestBody MongoTestRequest request){
        Page<MongoTest> testPage = mongoTestDao.searchPage(request);
        Page<MongoTestResponse> result = testPage.convert(mongoTest -> {
            // 分页的records的一个循环,这里可以做一些处理 - 相当于循环处理数据
            MongoTestResponse testResponse = EntityUtil.copyBean(mongoTest,MongoTestResponse.class);
            testResponse.setCreateTime(mongoTest.getCreateTime() != null ? DateUtil.formatDateTime(mongoTest.getCreateTime()) : "");
            return testResponse;
        });
        return result;
    }

    @ApiOperation(value = "测试playerNames",notes = "测试playerNames")
    @PostMapping("/playerNames")
    public JSONObject playerNames(String playName){
        JSONObject result = PubgApi.useApi(PubgApiEnum.PUBG_API_PLAYER_NAME).call(playName);
        return result;
    }

    @ApiOperation(value = "测试accountId",notes = "测试accountId")
    @PostMapping("/accountId/{accountId}")
    public JSONObject accountId(@PathVariable String accountId){
        JSONObject result = PubgApi.useApi(PubgApiEnum.PUBG_API_PLAYER_ACCOUNT).call(accountId);
        return result;
    }

    @ApiOperation(value = "测试matches",notes = "测试matches")
    @PostMapping("/matches/{id}")
    public JSONObject matches(@PathVariable String id){
        JSONObject result = PubgApi.useApi(PubgApiEnum.PUBG_API_MATCHES_ID).call(id);
        return result;
    }
}
