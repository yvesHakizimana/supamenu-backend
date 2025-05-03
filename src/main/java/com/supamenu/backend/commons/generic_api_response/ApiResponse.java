package com.supamenu.backend.commons.generic_api_response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.supamenu.backend.commons.pagination.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields in the request.
public class ApiResponse<T> {
    private String message;
    private T data;
    private PageInfo pagination;
    private boolean success;

    public static <T> ApiResponse<T> success(String message, T data){
        return ApiResponse.<T>builder()
                .message(message)
                .data(data)
                .success(true)
                .build();
    }


    public static <T> ApiResponse<T> success(String message, T data, PageInfo pagination){
        return ApiResponse.<T>builder()
                .message(message)
                .data(data)
                .pagination(pagination)
                .success(true)
                .build();
    }

    public static PageInfo fromSpringPage(Page page){
        return new PageInfo(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

}
