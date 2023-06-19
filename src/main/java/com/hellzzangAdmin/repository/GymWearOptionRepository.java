package com.hellzzangAdmin.repository;

import com.hellzzangAdmin.entity.GymWearOption;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.hellzzangAdmin.repository
 * fileName       : GymWearOptionRepository
 * author         : 김재성
 * date           : 2023-06-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-06-14        김재성       최초 생성
 */
public interface GymWearOptionRepository extends JpaRepository<GymWearOption, Long> {
    Long deleteByGymWearId(Long bannerId);
}
