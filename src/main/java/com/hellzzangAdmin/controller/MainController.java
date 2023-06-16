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
 * description    : 메인 controller
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-10        김재성       최초 생성
 */

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    /**
    * @methodName : list
    * @date : 2023-06-17 오전 12:00
    * @author : 김재성
    * @Description: 대시보드 (지금은 사용안함) 나중에 사용할수도 있어서 놔둠
    **/
    @GetMapping("/")
    public String list(String searchVal){
        return "views/main/dashboard";
    }

}
