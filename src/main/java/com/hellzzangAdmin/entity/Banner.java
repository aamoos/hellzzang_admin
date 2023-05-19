package com.hellzzangAdmin.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.time.LocalDateTime;

/**
 * packageName    : com.hellzzangAdmin.entity
 * fileName       : Banner
 * author         : 김재성
 * date           : 2023-05-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-16        김재성       최초 생성
 */

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bannerPath;

    private String delYn;

    private Long fileTotal;

    @CreatedDate
    private LocalDateTime regDate;     //등록날짜

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_index")
    private AdminUsers adminUsers;

    @Builder
    public Banner(Long id, String bannerPath, Long fileTotal, AdminUsers adminUsers){
        this.id = id;
        this.bannerPath = bannerPath;
        this.delYn = "N";
        this.fileTotal = fileTotal;
        this.adminUsers = adminUsers;
    }

    public Banner delete(){
        this.delYn = "Y";
        return this;
    }

}
