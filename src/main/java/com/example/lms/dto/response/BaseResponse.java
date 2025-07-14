package com.example.lms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
    private String message;
    private T data;

    public static <T> BaseResponse<T> of(String message, T data) {
        return BaseResponse.<T>builder()
                .message(message)
                .data(data)
                .build();
    }

}
