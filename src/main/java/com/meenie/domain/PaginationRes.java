package com.meenie.domain;

import lombok.Data;

@Data
public class PaginationRes {
    private Long total;
    private Long pageIndex;
    private Long pageSize;
    private Object data;
}
