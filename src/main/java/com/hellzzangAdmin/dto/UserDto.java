package com.hellzzangAdmin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Optional;

/**
* @package : com.example.jwt.dto
* @name : UserDto.java
* @date : 2023-04-19 오후 5:26
* @author : hj
* @Description: 회원정보 검색 시 사용
**/
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

    @JsonIgnore
    @Id
    @Column(name = "user_index")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //사용자 인덱스

    @NotNull
    @Size(max = 50)
    private String userid;

    @NotNull(message = "이름은 필수입력 값입니다.")
    @Pattern(regexp = "^([가-힣]{2,4}|[a-zA-Z]{2,10})\\s?([a-zA-Z]{2,10})?$", message = "한글 이름은 2~4글자, 영어 이름은 앞뒤 각각 2~10글자 작성해야 합니다.")
    private String username;

    @NotNull(message = "닉네임은 필수입력 값입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,12}$", message = "닉네임은 2~15글자 사이어야 합니다.")
    private String nickname;

    @NotNull(message = "주소는 필수입력 값입니다.")
    @Size(max = 50)
    private String address;

    @NotNull(message = "상세주소는 필수입력 값입니다.")
    @Size(max = 50)
    private String addressDetail;

    @NotNull(message = "번호는 필수입력 값입니다.")
    @Pattern(regexp = "^01[01][0-9]{7,8}$", message = "- 제외하여 입력해야 합니다.")
    private String phone;

    private String dorYn; //휴면상태여부

    private String delYn; //회원삭제여부

    private LocalDateTime joinDate; //가입날짜

    private String blockYn; //정지여부

    private LocalDateTime blockDate; //정지날짜

    @QueryProjection
    public UserDto(Long id, String nickname, String username, LocalDateTime joinDate, String dorYn, String delYn, String blockYn) {
        this.id = id;
        this.nickname = nickname;
        this.username = username;
        this.joinDate = joinDate;
        this.dorYn = dorYn;
        this.delYn = delYn;
        this.blockYn = blockYn;
    }
}