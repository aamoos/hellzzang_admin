package com.hellzzangAdmin.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * packageName    : com.hellzzangAdmin.entity
 * fileName       : GymWear
 * author         : 김재성
 * date           : 2023-05-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-22        김재성       최초 생성
 */

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class GymWear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @Column(name = "text_area", columnDefinition = "CLOB")
    private String contents;

    private String contentsText;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_index")
    private AdminUsers adminUsers;

    @CreatedDate
    private String createdDate;

    @LastModifiedDate
    private String modifiedDate;

    @PrePersist
    public void onPrePersist(){
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.modifiedDate = this.createdDate;
    }

    @PreUpdate
    public void onPreUpdate(){
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private Long thumbnailIdx;

    private String delYn;

    @Builder
    public GymWear(Long id, String title, String contents, String contentsText, AdminUsers adminUsers, Long thumbnailIdx, String delYn){
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.contentsText = contentsText;
        this.adminUsers = adminUsers;
        this.thumbnailIdx = thumbnailIdx;
        this.delYn = "N";
    }

    public GymWear delete(){
        this.delYn = "Y";
        return this;
    }

}
