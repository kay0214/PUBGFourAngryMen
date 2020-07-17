/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.base;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * @author sunpeikai
 * @version Page, v0.1 2020/7/10 11:34
 * @description
 */
public class Page<T> implements Serializable {

    // 数据列表
    private List<T> records = Collections.emptyList();

    // 总数
    private int total = 0;

    // 每页显示条数，默认 10
    private int size = 10;

    // 当前页
    private int current = 1;

    // 总页数
    private int pages = 0;

    public Page() {
    }

    public Page(List<T> records, int total, int size, int current) {
        this.records = records;
        this.total = total;
        this.size = size;
        this.current = current;
    }

    // getter
    public List<T> getRecords() {
        return records;
    }

    public int getTotal() {
        return total;
    }

    public int getSize() {
        return size;
    }

    public int getCurrent() {
        return current;
    }

    public int getPages() {
        return pages;
    }

    // setter
    public Page<T> records(List<T> records) {
        this.records = records;
        return this;
    }

    public Page<T> total(int total) {
        this.total = total;
        this.setPages();
        return this;
    }

    public Page<T> size(int size) {
        this.size = size;
        this.setPages();
        return this;
    }

    public Page<T> current(int current) {
        this.current = current;
        return this;
    }

    public void setPages(){
        // 总页数为0且满足条件才计算总页数
        if(this.pages == 0 && this.total != 0 && size != 0){
            this.pages = this.total % this.size == 0 ? this.total/this.size : this.total/this.size + 1;
        }
    }

    /**
     * Page 转换
     *
     * @param mapper 转换函数
     * @param <R>    转换后的泛型
     * @return 转换泛型后的 Page
     */
    @SuppressWarnings("unchecked")
    public <R> Page<R> convert(Function<? super T, ? extends R> mapper) {
        List<R> collect = this.getRecords().stream().map(mapper).collect(toList());
        return ((Page<R>) this).records(collect);
    }
}
