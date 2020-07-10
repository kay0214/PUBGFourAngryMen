/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.service;

import com.pubg.analysis.base.MongoBaseDao;
import com.pubg.analysis.base.Page;
import com.pubg.analysis.entity.MongoTest;
import com.pubg.analysis.request.MongoTestRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


/**
 * @author sunpeikai
 * @version MongoTest, v0.1 2020/7/10 10:42
 * @description
 */
@Repository
public class MongoTestDao extends MongoBaseDao<MongoTest> {

    @Override
    protected Class<MongoTest> getEntityClass() {
        return MongoTest.class;
    }

    /**
     * @description 搜索分页
     * @auth sunpeikai
     * @param
     * @return
     */
    public Page<MongoTest> searchPage(MongoTestRequest request){
        Query query = new Query();
        // 构建查询条件
        Criteria criteria = new Criteria();
        // 检索条件
        if(request.getClassId() != null){
            criteria.and("classId").is(request.getClassId());
        }
        // 检索条件 - 老师
        if(!StringUtils.isEmpty(request.getTeacher())){
            criteria.and("teacher").is(request.getTeacher());
        }
        // 检索条件 - 是否班主任
        if(request.getHeadmaster() != null){
            criteria.and("headmaster").is(request.getHeadmaster());
        }
        // 检索条件 - 课程
        if(!StringUtils.isEmpty(request.getCourse())){
            criteria.and("course").is(request.getCourse());
        }
        // 添加查询条件 + 排序
        query.addCriteria(criteria).with(Sort.by(Sort.Direction.DESC,"classId"));
        // 处理数据
        return page(query,request.getCurrPage(),request.getPageSize());
    }
}
