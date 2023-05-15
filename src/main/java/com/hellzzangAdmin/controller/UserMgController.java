package com.hellzzangAdmin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.hellzzangAdmin.controller
 * fileName       : MemberManageController
 * author         : 김재성
 * date           : 2023-05-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-15        김재성       최초 생성
 */

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/manage")
public class UserMgController {

    /**
     * @methodName : user
     * @date : 2023-05-11 오후 12:54
     * @author : 김재성
     * @Description: 회원 등록 리스트
     **/
    @GetMapping("/user/list")
    public String user(){
        return "views/manage/user/user-list";
    }

    /**
     * @methodName : userSave
     * @date : 2023-05-11 오후 3:39
     * @author : 김재성
     * @Description: 사용자 등록 화면
     **/
    @GetMapping("/user/save")
    public String userSave(){
        return "views/manage/user/user-save";
    }
}
