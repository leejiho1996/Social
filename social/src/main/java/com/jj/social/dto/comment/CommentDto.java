package com.jj.social.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDto {
    @NotBlank
    private String content;

    @NotNull
    private Long imageId;
}
