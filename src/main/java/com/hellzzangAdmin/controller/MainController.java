package com.hellzzangAdmin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName    : com.hellzzangAdmin.controller
 * fileName       : MainController
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
public class MainController {
    @GetMapping("/")
    public String list(String searchVal){
        return "views/main";
    }

}
