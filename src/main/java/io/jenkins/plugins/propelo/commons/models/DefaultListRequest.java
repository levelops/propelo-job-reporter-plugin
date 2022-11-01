package io.jenkins.plugins.propelo.commons.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class DefaultListRequest {
    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_PAGE_SIZE = 100;

    @JsonProperty("page")
    private int page;
    @JsonProperty("page_size")
    private int pageSize;

    public DefaultListRequest() {
        this.page = DEFAULT_PAGE;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public DefaultListRequest(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultListRequest that = (DefaultListRequest) o;
        return page == that.page &&
                pageSize == that.pageSize;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(page, pageSize);
    }

    @Override
    public String toString() {
        return "DefaultListRequest{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}
