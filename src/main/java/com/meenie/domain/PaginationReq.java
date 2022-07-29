package com.meenie.domain;

import lombok.Data;

@Data
public class PaginationReq {
    private Long pageIndex;
    private Long pageSize;
}
