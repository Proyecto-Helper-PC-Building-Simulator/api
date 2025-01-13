package es.bit.api.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 *  Paged response containing a list of items, paging information and paging statistics.
 *
 * @param <T> Type of elements contained in the paged response.
 */
@Getter
@Setter
public class PagedResponse<T> {
    private List<T> content;
    private boolean last;
    private boolean empty;
    private boolean hasContent;
    private int page;
    private int size;
    private int number;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "componentId";
    private long totalElements;
    private int totalPages;


    public static <T> PagedResponse<T> toCustomPageResponse(Page<T> page) {
        PagedResponse<T> response = new PagedResponse<>();
        response.setContent(page.getContent());
        response.setLast(page.isLast());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setSize(page.getSize());
        response.setNumber(page.getNumber());
        response.setEmpty(page.isEmpty());
        response.setHasContent(page.hasContent());

        return response;
    }
}
