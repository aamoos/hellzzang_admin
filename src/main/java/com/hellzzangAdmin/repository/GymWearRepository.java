package com.hellzzangAdmin.repository;

import com.hellzzangAdmin.entity.GymWear;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.hellzzangAdmin.repository
 * fileName       : GymWearRepository
 * author         : 김재성
 * date           : 2023-05-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-22        김재성       최초 생성
 */
public interface GymWearRepository extends JpaRepository<GymWear, Long> {

}