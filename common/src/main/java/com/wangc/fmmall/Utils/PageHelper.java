package com.wangc.fmmall.Utils;

import java.util.List;

public class PageHelper<T> {
    // 总记录数
    private int count;

    // 总页数
    private int pageCount;

    // 分页数据
    private List<T> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public PageHelper() {
    }

    public PageHelper(int count, int pageCount, List<T> list) {
        this.count = count;
        this.pageCount = pageCount;
        this.list = list;
    }
}
