package com.hellzzangAdmin.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * packageName    : com.hellzzangAdmin.dto
 * fileName       : BannerDto
 * author         : 김재성
 * date           : 2023-05-16
 * description    : 배너 select Dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-16        김재성       최초 생성
 */

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BannerDto {

    @Id
    @GeneratedValue
    private Long id;

    private String bannerPath;

    private String delYn;

    private Long regUserIdx;

    private String regUserName;

    private Long fileTotal;

    private String createdDate;

    @QueryProjection
    public BannerDto(Long id, String bannerPath, String delYn, Long regUserIdx, String regUserName, Long fileTotal, String createdDate) {
        this.id = id;
        this.bannerPath = bannerPath;
        this.delYn = delYn;
        this.regUserIdx = regUserIdx;
        this.regUserName = regUserName;
        this.fileTotal = fileTotal;
        this.createdDate = createdDate;
    }

}
