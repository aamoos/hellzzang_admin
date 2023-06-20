package com.hellzzangAdmin.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * packageName    : com.hellzzangAdmin.entity
 * fileName       : BannerFile
 * author         : 김재성
 * date           : 2023-05-17
 * description    : 짐웨어 파일 entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-17        김재성       최초 생성
 */

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GymWearFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gymWear_id")
    private GymWear gymWear;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private FileInfo fileInfo;

    @Builder
    public GymWearFile(Long id, GymWear gymWear, FileInfo fileInfo){
        this.id = id;
        this.gymWear = gymWear;
        this.fileInfo = fileInfo;
    }

}
