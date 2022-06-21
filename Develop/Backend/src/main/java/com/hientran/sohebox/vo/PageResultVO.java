package com.hientran.sohebox.vo;

import java.util.List;

/**
 * @author hientran
 */
public class PageResultVO<T> extends BaseVO {

    private static final long serialVersionUID = 1L;

    private long totalPage;

    private long totalElement;

    private long currentPage;

    private long pageSize;

    private List<T> elements;

    /**
     * 
     * Constructor
     *
     */
    public PageResultVO() {
        super();
    }

    /**
     * Get totalPage
     *
     * @return totalPage
     */
    public long getTotalPage() {
        return totalPage;
    }

    /**
     * Set totalPage
     *
     * @param totalPage
     *            the totalPage to set
     */
    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * Get totalElement
     *
     * @return totalElement
     */
    public long getTotalElement() {
        return totalElement;
    }

    /**
     * Set totalElement
     *
     * @param totalElement
     *            the totalElement to set
     */
    public void setTotalElement(long totalElement) {
        this.totalElement = totalElement;
    }

    /**
     * Get elements
     *
     * @return elements
     */
    public List<T> getElements() {
        return elements;
    }

    /**
     * Set elements
     *
     * @param elements
     *            the elements to set
     */
    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    /**
     * Get currentPage
     *
     * @return currentPage
     */
    public long getCurrentPage() {
        return currentPage;
    }

    /**
     * Set currentPage
     *
     * @param currentPage
     *            the currentPage to set
     */
    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageResultVO [totalPage=" + totalPage + ", totalElement=" + totalElement + ", currentPage="
                + currentPage + ", pageSize=" + pageSize + ", elements=" + elements + "]";
    }

}