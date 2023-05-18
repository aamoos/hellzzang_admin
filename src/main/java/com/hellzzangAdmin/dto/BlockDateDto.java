package com.hellzzangAdmin.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * packageName    : com.hellzzangAdmin.dto
 * fileName       : BlockDateDto
 * author         : hj
 * date           : 2023-05-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-16        hj       최초 생성
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlockDateDto {

    @NotNull
    private Long id;

    @NotNull
    private String selectVal;

    @QueryProjection
    public BlockDateDto(Long id, String selectVal){
        this.id = id;
        this.selectVal = selectVal;
    }
}
