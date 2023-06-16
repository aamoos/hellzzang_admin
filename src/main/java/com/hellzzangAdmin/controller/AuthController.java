package com.hellzzangAdmin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.hellzzangAdmin.controller
 * fileName       : AuthController
 * author         : 김재성
 * date           : 2023-05-10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-10        김재성       최초 생성
 */

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    /**
    * @methodName : list
    * @date : 2023-06-16 오후 11:57
    * @author : 김재성
    * @Description: 사용자 로그인 화면
    **/
    @GetMapping("/login")
    public String list(String searchVal){
        return "views/auth/login";
    }

}
