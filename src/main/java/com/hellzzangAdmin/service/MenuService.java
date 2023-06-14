package com.hellzzangAdmin.service;

import com.hellzzangAdmin.dto.MenuDto;
import com.hellzzangAdmin.entity.Menu;
import com.hellzzangAdmin.entity.QMenu;
import com.hellzzangAdmin.repository.MenuRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


/**
 * packageName    : com.hellzzangAdmin.service
 * fileName       : MenuService
 * author         : hj
 * date           : 2023-05-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-22        hj       최초 생성
 */

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    private final JPAQueryFactory jpaQueryFactory;

    public List<MenuDto> findAllWithQuerydsl() {
        QMenu parent = new QMenu("parent");
        QMenu child = new QMenu("child");

        List<Menu> result = jpaQueryFactory.selectFrom(parent)
                .distinct()
                .leftJoin(parent.children, child)
                .fetchJoin()
                .where(parent.parent.isNull())
                .orderBy(parent.depth.desc(), child.depth.desc())
                .fetch();

        return result.stream().map(MenuDto::new).collect(Collectors.toList());
    }

}