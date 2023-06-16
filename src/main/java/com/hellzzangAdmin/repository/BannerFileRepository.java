package com.hellzzangAdmin.repository;

import com.hellzzangAdmin.entity.BannerFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.hellzzangAdmin.repository
 * fileName       : BannerRepository
 * author         : 김재성
 * date           : 2023-05-16
 * description    : 배너 파일 repository
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-16        김재성       최초 생성
 */
public interface BannerFileRepository extends JpaRepository<BannerFile, Long> {

    Long deleteByBannerId(Long bannerId);
}
