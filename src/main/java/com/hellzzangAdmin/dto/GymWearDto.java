package com.hellzzangAdmin.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

/**
 * packageName    : com.hellzzangAdmin.dto
 * fileName       : BannerDto
 * author         : 김재성
 * date           : 2023-05-16
 * description    : 짐웨어 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-16        김재성       최초 생성
 */

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GymWearDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @Column(name = "text_area", columnDefinition = "CLOB")
    private String contents;

    private String contentsText;

    private String regUserName;

    private String createdDate;

    private String modifiedDate;

    private Long thumbnailIdx;

    private String delYn;

    private Long price;

    private String optionYn;

    @QueryProjection
    public GymWearDto(Long id, String title, String contents, String contentsText, String regUserName, String createdDate, String modifiedDate, Long thumbnailIdx, String delYn, Long price, String optionYn) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.contentsText = contentsText;
        this.regUserName = regUserName;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.thumbnailIdx = thumbnailIdx;
        this.delYn = delYn;
        this.price = price;
        this.optionYn = optionYn;
    }

}
