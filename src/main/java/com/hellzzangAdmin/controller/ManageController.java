package com.hellzzangAdmin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.hellzzangAdmin.controller
 * fileName       : ManageController
 * author         : 김재성
 * date           : 2023-05-11
 * description    : 관리controller
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

    /**
    * @methodName : category
    * @date : 2023-05-11 오전 10:47
    * @author : 김재성
    * @Description: 카테고리 리스트
    **/
    @GetMapping("/menu/list")
    public String menu(){
        return "views/manage/menu/menu-list";
    }

    /**
    * @methodName : community
    * @date : 2023-05-11 오전 10:47
    * @author : 김재성
    * @Description: 커뮤니티 리스트
    **/
    @GetMapping("/community/list")
    public String community(){
        return "views/manage/community/community-list";
    }
}
