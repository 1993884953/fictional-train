package com.example.demo.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;

@ToString
@JsonIgnoreProperties(value = {"offset"})
public class Pagination {

    protected int page;
    protected int limit;
    protected long total;

    public Pagination() {
        limit = 5;
        page = 1;
    }

    public Pagination(int page, int limit) {
        setPage(page);
        setLimit(limit);
    }

    public Pagination(int page, int limit, long total) {
        setPage(page);
        setLimit(limit);
        setTotal(total);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page > 0 ? page : 1;
    }

    public int getOffset() {
        return (page - 1) * limit;
    }

    public void setLimit(int limit) {
        if (limit > 0) {
            this.limit = limit;
        }
    }

    public int getLimit() {
        return limit;
    }
}