package es.bit.api.utils;

import org.springframework.data.domain.Sort;

import java.util.List;

/**
 *  Paged response containing a list of items, paging information and paging statistics.
 *
 * @param <T> Type of elements contained in the paged response.
 */
public class PagedResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "componentId";
    private long totalElements;
    private int totalPages;


    /**
     * PagedResponse constructor.
     *
     * @param content List of elements present in the current page.
     * @param page Number of the actual page.
     * @param size Size of the actual page.
     * @param totalElements Total number of elements on all pages.
     * @param totalPages Total number of pages available.
     */
    public PagedResponse(List<T> content, int page, int size, long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }


    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
