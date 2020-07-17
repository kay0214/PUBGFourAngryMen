package com.pubg.analysis.response;

import com.pubg.analysis.entity.log.BaseLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.TreeMap;

/**
 * 位置响应
 *
 * @author yangy
 * @date 2020/7/13 10:05
 */
@Data
@ApiModel("位置信息返回参数")
public class PositionResponse {

    @ApiModelProperty("起始时间")
    private long start = 0;

    @ApiModelProperty("终止时间")
    private long end = 0;

    @ApiModelProperty("位置信息")
    private TreeMap<Long, List<BaseLog>> positions;
}
