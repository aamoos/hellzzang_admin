package com.hellzzangAdmin.controller;

import com.hellzzangAdmin.dto.BlockDateDto;
import com.hellzzangAdmin.dto.UserDto;
import com.hellzzangAdmin.repository.UserRepository;
import com.hellzzangAdmin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

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

    private final UserService userService;
    private final UserRepository userRepository;

    /**
     * @methodName : user
     * @date : 2023-05-11 오후 12:54
     * @author : 김재성
     * @Description: 회원 등록 리스트
     **/
    @GetMapping("/user/list")
    public String user(String searchVal, Model model){

        List<UserDto> list = userService.selectUserInfo(searchVal);
        model.addAttribute("infoList", list);

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
    
    /**
     * @methodName : userBlock
     * @date : 2023-05-16 오전 10:46
     * @author : hj
     * @Description: 사용자 계정 정지 처리
     **/
    @PostMapping("/user/block")
    @ResponseBody
    public long userBlock(@Valid @RequestBody BlockDateDto request){
        return userService.saveUserBlockDay(request.getId(), request.getSelectVal());
    }
}
