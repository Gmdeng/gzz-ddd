package com.gzz.core.toolkit;

/**
 * 分页工具类
 *
 * @author Gm
 */
public class Pager {
    private int pageSize = 25; // 页面记录大小
    private int totalRecord = 0; // 总记录
    private int totalPage = 0; // 总页数
    private int indexPage = 1; // 当前页
    private int offset = 0; // 上次记录位置

    /**
     * 构造函数
     */
    public Pager() {

    }

    /**
     * 构造函数 *
     *
     * @param pageSize 页大小
     */
    public Pager(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
        this.totalPage = (this.totalRecord % this.pageSize == 0) ? (this.totalRecord / this.pageSize)
                : (this.totalRecord / this.pageSize + 1);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getOffset() {
        return this.pageSize * (this.indexPage - 1);
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public int getIndexPage() {
        return this.indexPage;
    }

    public void setIndexPage(int indexPage) {
        this.indexPage = indexPage;
    }
}
