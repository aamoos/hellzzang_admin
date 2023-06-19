package com.hellzzangAdmin.entity;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Optional;

/**
 * packageName    : com.hellzzangAdmin.entity
 * fileName       : GymWearOption
 * author         : 김재성
 * date           : 2023-06-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-06-14        김재성       최초 생성
 */

@Entity
public class GymWearOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionName;

    private Long gymWearId;

    public GymWearOption(String option, Long gymWearId) {
        this.optionName = option;
        this.gymWearId = gymWearId;
    }
}
