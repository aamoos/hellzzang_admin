package com.hellzzangAdmin.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @package : com.hellzzangAdmin.entity
* @name : Authority.java
* @date : 2023-06-17 오전 12:21
* @author : 김재성
* @Description: 사용자 권한 entity
**/

@Entity
@Table(name = "authority")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;
}