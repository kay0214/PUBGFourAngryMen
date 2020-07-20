/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author sunpeikai
 * @version BaseRequest, v0.1 2020/7/10 11:22
 * @description
 */
@Data
public class BaseRequest implements Serializable {
    @ApiModelProperty(value = "当前页码")
    private int page = 1;

    @ApiModelProperty(value = "每页记录数")
    private int limit = 10;

}
