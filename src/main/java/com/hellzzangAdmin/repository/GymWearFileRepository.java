package com.hellzzangAdmin.repository;

import com.hellzzangAdmin.entity.GymWearFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.hellzzangAdmin.repository
 * fileName       : BannerRepository
 * author         : 김재성
 * date           : 2023-05-16
 * description    : 짐웨어 파일 repository
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-16        김재성       최초 생성
 */
public interface GymWearFileRepository extends JpaRepository<GymWearFile, Long> {

    Long deleteByGymWearId(Long bannerId);
}
