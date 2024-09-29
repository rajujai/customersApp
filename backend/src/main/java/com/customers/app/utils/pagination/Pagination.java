package com.customers.app.utils.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {
    private int pageNo = 0;
    private int pageSize = 10;
    private String sortBy;
    private Sort.Direction order = ASC;

    public PageRequest pageRequest() {
        if (Strings.isBlank(sortBy)) return PageRequest.of(pageNo, pageSize);
        else return PageRequest.of(pageNo, pageSize, Sort.by(order, sortBy));
    }
}
