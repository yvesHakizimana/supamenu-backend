package com.supamenu.backend.commons.pagination;

public record PageInfo(
        Integer pageNumber,
        Integer pageSize,
        Long totalElements,
        Integer totalPages
){}