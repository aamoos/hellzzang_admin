package com.hellzzangAdmin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * packageName    : com.hellzzangAdmin.dto
 * fileName       : AdminUserDto
 * author         : 김재성
 * date           : 2023-05-12
 * description    : 관리자 사용자 select dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-12        김재성       최초 생성
 */

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminUsersDto {

    @JsonIgnore
    @Id
    @Column(name = "user_index")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //사용자 인덱스

    @Column(name = "userid", length = 50, unique = true)
    private String userid; //로그인 아이디

    @Column(name = "password", length = 100)
    private String password; //비밀번호

    @Column(name = "username", length = 50)
    private String username; //이름

    private String createdDate;     //등록날짜

    private String delYn;   //삭제여부

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

    @QueryProjection
    public AdminUsersDto(Long id, String userid, String username, String delYn, String createdDate) {
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.delYn = delYn;
        this.createdDate = createdDate;
    }


}
