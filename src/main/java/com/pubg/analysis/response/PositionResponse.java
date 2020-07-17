package com.pubg.analysis.response;

import com.pubg.analysis.entity.log.Character;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 位置响应
 *
 * @author yangy
 * @date 2020/7/13 10:05
 */
@Data
@ApiModel("位置信息返回参数")
public class PositionResponse implements Serializable {

    @ApiModelProperty("起始时间")
    private long start = 0;

    @ApiModelProperty("终止时间")
    private long end = 0;

    @ApiModelProperty(value = "位置信息", notes = "<时间点, Map<accountId,Character>>")
    private TreeMap<Long, Map<String, Character>> positions;

    @ApiModelProperty("地图类型")
    private String mapType;

    @ApiModelProperty("死亡记录")
    private Map<String, Long> deathLog;

    @ApiModelProperty(value = "角色列表", notes = "包括电脑")
    private Map<String, Character> characters;

    @ApiModelProperty(value = "角色维度位置追踪", notes = "<accountId, [[xRatio, yRatio]]>")
    Map<String, List<List<Double>>> playerTrack;
}
