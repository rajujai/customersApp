package com.customers.app.utils.pagination;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Data
public class PageResponse<T> {
    private List<T> data;
    private Pagination page;
    private long totalPages;
    private long totalElements;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean isSorted;

    public static <T> PageResponse<T> ofPage(Page<T> page, Pagination pagination) {
        return PageResponse.<T>builder()
                .data(page.getContent())
                .page(pagination)
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isFirstPage(page.isFirst())
                .isLastPage(page.isLast())
                .isSorted(page.getSort().isSorted())
                .build();
    }
}
