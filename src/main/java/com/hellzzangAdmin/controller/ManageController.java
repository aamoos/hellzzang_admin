package com.hellzzangAdmin.controller;

import com.hellzzangAdmin.dto.MenuDto;
import com.hellzzangAdmin.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * packageName    : com.hellzzangAdmin.controller
 * fileName       : ManageController
 * author         : 김재성
 * date           : 2023-05-11
 * description    : 관리 controller
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-11        김재성       최초 생성
 */

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/manage")
public class ManageController {

    private final MenuService menuService;

    /**
     * @methodName : category
     * @date : 2023-05-11 오전 10:47
     * @author : 김재성
     * @Description: 카테고리 리스트
     **/
    @GetMapping("/menu/list")
    public String menu(Model model){

        List<MenuDto> result = menuService.findAllWithQuerydsl();
        System.out.println(result.size());
        List<MenuDto> frontResult = new ArrayList<>();
        for(int i=0 ; i<result.size(); i++){
            for(int j = 0; j<result.get(i).getChildren().size(); j++){
                frontResult.add(i, result.get(i).getChildren().get(j));
            }
        }

        Collections.sort(frontResult, new Comparator<MenuDto>() {
            @Override
            public int compare(MenuDto dto1, MenuDto dto2) {
                return dto1.getName().compareTo(dto2.getName());
            }
        });
        model.addAttribute("commuList", frontResult);
        model.addAttribute("bigMenu", result);

        return "views/manage/menu/menu-list";
    }

    /**
     * @methodName : community
     * @date : 2023-05-11 오전 10:47
     * @author : 김재성
     * @Description: 커뮤니티 리스트
     **/
    @GetMapping("/community/list")
    public String community(Model model){


        return "views/manage/community/community-list";
    }
}
