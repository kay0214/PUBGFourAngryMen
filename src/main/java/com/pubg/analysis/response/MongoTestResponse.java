/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author sunpeikai
 * @version MongoTestResponse, v0.1 2020/7/10 10:53
 * @description
 */
@Data
@ApiModel("mongo测试返回参数")
public class MongoTestResponse {

    // 班级ID
    @ApiModelProperty("班级ID")
    private Integer classId;

    // 老师
    @ApiModelProperty("老师")
    private String teacher;

    // 是否班主任
    @ApiModelProperty("是否班主任")
    private Integer headmaster;

    // 所授课程
    @ApiModelProperty("所授课程")
    private String course;

    // 创建时间
    @ApiModelProperty("创建时间")
    private String createTime;
}
