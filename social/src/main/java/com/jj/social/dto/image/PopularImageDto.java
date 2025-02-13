package com.jj.social.dto.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PopularImageDto {
    private String imageName;
    private Integer likeCount;
    private Long userId;
}
