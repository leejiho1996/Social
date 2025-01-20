package com.jj.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 공통응답 DTO
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMRespDto<T> {
    private Integer code;
    private String message;
    private T data;
}
