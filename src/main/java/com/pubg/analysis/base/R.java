/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @author sunpeikai
 * @version R, v0.1 2020/7/20 13:26
 * @description
 */
@Data
@ApiModel(description = "返回信息")
@NoArgsConstructor
public class R<T> implements Serializable {
    @ApiModelProperty(value = "状态码", required = true)
    private int status;

    @ApiModelProperty(value = "承载数据")
    private T data;

    @ApiModelProperty(value = "返回消息", required = true)
    private String statusDesc;

    private R(int status, T data, String statusDesc) {
        this.status = status;
        this.data = data;
        this.statusDesc = statusDesc;
    }

    /**
     * 返回R
     *
     * @param data 数据
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> R<T> data(T data) {
        return data(data, "操作成功");
    }

    /**
     * 返回R
     *
     * @param data 数据
     * @param statusDesc  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> R<T> data(T data, String statusDesc) {
        statusDesc = StringUtils.isEmpty(statusDesc)?"操作成功":statusDesc;
        return data(0, data, statusDesc);
    }

    /**
     * 返回R
     *
     * @param status 状态码
     * @param data 数据
     * @param statusDesc  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> R<T> data(int status, T data, String statusDesc) {
        statusDesc = StringUtils.isEmpty(statusDesc)?"操作成功":statusDesc;
        return new R<>(status, data, data == null ? "暂无数据" : statusDesc);
    }

    /**
     * 返回R
     *
     * @param statusDesc  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> R<T> fail(String statusDesc) {
        return new R<>( 99, null, statusDesc);
    }

    /**
     * 返回R
     *
     * @param status 状态码
     * @param statusDesc  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> R<T> fail(int  status, String statusDesc) {
        return new R<>( status, null, statusDesc);
    }
}
