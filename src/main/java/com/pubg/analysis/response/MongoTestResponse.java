/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.response;

import lombok.Data;

/**
 * @author sunpeikai
 * @version MongoTestResponse, v0.1 2020/7/10 10:53
 * @description
 */
@Data
public class MongoTestResponse {
    // 班级ID
    private Integer classId;

    // 老师
    private String teacher;

    // 是否班主任
    private Integer headmaster;

    // 所授课程
    private String course;

    // 查询时间
    private String queryTime;
}
