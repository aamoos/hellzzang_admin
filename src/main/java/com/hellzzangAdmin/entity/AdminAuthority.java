package com.hellzzangAdmin.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
* @package : com.hellzzangAdmin.entity
* @name : AdminAuthority.java
* @date : 2023-06-17 오전 12:21
* @author : 김재성
* @Description: 관리자 권한 entity
**/

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminAuthority {

    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;
}