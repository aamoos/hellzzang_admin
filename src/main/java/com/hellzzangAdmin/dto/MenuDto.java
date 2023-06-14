package com.hellzzangAdmin.dto;

import com.hellzzangAdmin.entity.Menu;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : com.hellzzangAdmin.dto
 * fileName       : MenuResultDto
 * author         : hj
 * date           : 2023-05-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-22        hj       최초 생성
 */

@Data
@NoArgsConstructor
public class MenuDto {

    private Long id;
    private String name;
    private int depth;
//    private Menu parent;
    private List<MenuDto> children;

    public MenuDto(final Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.depth = menu.getDepth();
        this.children = menu.getChildren().stream().map(MenuDto::new).collect(Collectors.toList());
    }
}