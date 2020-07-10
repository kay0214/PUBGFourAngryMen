/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.controller;

import com.pubg.analysis.base.Page;
import com.pubg.analysis.entity.MongoTest;
import com.pubg.analysis.request.MongoTestRequest;
import com.pubg.analysis.response.MongoTestResponse;
import com.pubg.analysis.service.MongoTestDao;
import com.pubg.analysis.utils.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunpeikai
 * @version TestMongo, v0.1 2020/7/10 10:49
 * @description
 */
@RestController
@RequestMapping("/test")
public class TestMongoController {

    @Autowired
    private MongoTestDao mongoTestDao;

    @PostMapping("/insert")
    public String insert(@RequestBody MongoTestRequest request){
        MongoTest mongoTest = EntityUtil.copyBean(request,MongoTest.class);
        mongoTestDao.insert(mongoTest);
        return "Ok";
    }

    @PostMapping("/insertBatch")
    public String insertBatch(@RequestBody MongoTestRequest request){
        List<MongoTest> mongoTests = new ArrayList<>();
        for(int i=1;i<=10;i++){
            MongoTest mongoTest = EntityUtil.copyBean(request,MongoTest.class);
            mongoTest.setClassId(i);
            mongoTest.setHeadmaster(i==1?1:0);
            mongoTests.add(mongoTest);
        }
        mongoTestDao.insertAll(mongoTests);
        return "Ok";
    }

    @PostMapping("/searchPage")
    public Page<MongoTestResponse> searchPage(@RequestBody MongoTestRequest request){
        Page<MongoTest> testPage = mongoTestDao.searchPage(request);
        Page<MongoTestResponse> result = testPage.convert(mongoTest -> {
            // 分页的records的一个循环,这里可以做一些处理 - 相当于循环处理数据
            MongoTestResponse testResponse = EntityUtil.copyBean(mongoTest,MongoTestResponse.class);
            testResponse.setQueryTime(LocalDateTime.now().toString());
            return testResponse;
        });
        return result;
    }
}
