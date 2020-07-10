/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.request;

import com.pubg.analysis.base.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sunpeikai
 * @version MongoTestRequest, v0.1 2020/7/10 10:52
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MongoTestRequest extends BaseRequest {

    // 班级ID
    private Integer classId;

    // 老师
    private String teacher;

    // 是否班主任
    private Integer headmaster;

    // 所授课程
    private String course;
}
