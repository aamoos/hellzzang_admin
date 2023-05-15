package com.hellzzangAdmin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * packageName    : com.hellzzangAdmin.dto
 * fileName       : AdminUserDto
 * author         : 김재성
 * date           : 2023-05-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-12        김재성       최초 생성
 */

@Data
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

    private LocalDateTime regDate;     //등록날짜

    private String delYn;   //삭제여부

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

    public AdminUsersDto() {
    }

    @QueryProjection
    public AdminUsersDto(Long id, String userid, String username, String delYn, LocalDateTime regDate) {
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.delYn = delYn;
        this.regDate = regDate;
    }


}
