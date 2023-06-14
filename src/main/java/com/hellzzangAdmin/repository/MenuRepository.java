package com.hellzzangAdmin.repository;

import com.hellzzangAdmin.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * packageName    : com.hellzzangAdmin.repository
 * fileName       : MenuRepository
 * author         : hj
 * date           : 2023-05-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-18        hj       최초 생성
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {

}
