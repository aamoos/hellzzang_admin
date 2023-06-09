package com.hellzzangAdmin.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hellzzangAdmin.dto.AdminUsersDto;
import com.hellzzangAdmin.entity.AdminUsers;
import com.hellzzangAdmin.repository.AdminUserRepository;
import com.hellzzangAdmin.service.AdminUserService;
import com.hellzzangAdmin.valid.auth.PasswordMatches;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : com.hellzzangAdmin.controller
 * fileName       : AdminMgController
 * author         : 김재성
 * date           : 2023-05-15
 * description    : 관리자 사용자 controller
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-15        김재성       최초 생성
 */

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/manage")
public class AdminMgController {

    private final AdminUserService adminUserService;
    private final AdminUserRepository adminUserRepository;

    /**
     * @methodName : adminUser
     * @date : 2023-05-11 오후 12:54
     * @author : 김재성
     * @Description: 관리자 등록 리스트
     **/
    @GetMapping("/adminUser/list")
    public String adminUser(String searchVal, @PageableDefault Pageable pageable, Model model){
        Page<AdminUsersDto> results = adminUserService.selectAdminUserList(searchVal, pageable);

        model.addAttribute("list", results);
        model.addAttribute("maxPage", 5);
        model.addAttribute("searchVal", searchVal);
        model.addAttribute("totalCount", results.getTotalElements());
        model.addAttribute("size",  results.getPageable().getPageSize());
        model.addAttribute("number",  results.getPageable().getPageNumber());

        return "views/manage/adminUser/adminUser-list";
    }

    /**
     * @methodName : adminUserWrite
     * @date : 2023-05-11 오후 3:38
     * @author : 김재성
     * @Description: 관리자 등록 화면
     **/
    @GetMapping("/adminUser/write")
    public String adminUserWrite(Model model){
        model.addAttribute("saveAdminUser", new SaveAdminUser());
        return "views/manage/adminUser/adminUser-write";
    }

    /**
     * @methodName : adminUserSaveForm
     * @date : 2023-05-12 오전 9:56
     * @author : 김재성
     * @Description: 관리자 등록 submit
     **/
    @PostMapping("/adminUser/write")
    public String adminUserWriteForm(@Valid SaveAdminUser saveAdminUser, BindingResult result) {

        if (result.hasErrors()) {
            List<ObjectError> globalErrors = result.getGlobalErrors();
            for (ObjectError ge : globalErrors) {
                if(ge.getCode().equals(PasswordMatches.class.getSimpleName())){
                    result.rejectValue("password", "Eqauls.pass", ge.getDefaultMessage());
                }
            }
            return "views/manage/adminUser/adminUser-write";
        }

        //아이디 중복체크
        if(adminUserRepository.findByUserid(saveAdminUser.getUserid()) != null){
            result.rejectValue("userid", "Eqauls.userid", "아이디가 중복되었습니다.");
            return "views/manage/adminUser/adminUser-write";
        }

        //저장
        adminUserService.save(saveAdminUser);

        // 검증 통과 시, 사용자 등록 로직 수행
        return "redirect:/manage/adminUser/list";
    }

    /**
     * @methodName : adminUserUpdate
     * @date : 2023-05-12 오후 3:57
     * @author : 김재성
     * @Description: 관리자 수정 화면
     **/
    @GetMapping("/adminUser/update/{id}")
    public String adminUserUpdate(@PathVariable Long id, Model model){
        AdminUsers adminUsers = adminUserService.find(id);

        SaveAdminUser saveAdminUser = SaveAdminUser.builder()
                .id(adminUsers.getId())
                .userid(adminUsers.getUserid())
                .username(adminUsers.getUsername())
                .delYn(adminUsers.getDelYn())
                .build();

        model.addAttribute("saveAdminUser", saveAdminUser);
        return "views/manage/adminUser/adminUser-update";
    }

    /**
    * @methodName : adminUserUpdateForm
    * @date : 2023-06-16 오후 11:56
    * @author : 김재성
    * @Description: 관리자 수정 submit
    **/
    @PostMapping("/adminUser/update/{id}")
    public String adminUserUpdateForm(@Valid SaveAdminUser saveAdminUser, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> globalErrors = result.getGlobalErrors();
            for (ObjectError ge : globalErrors) {
                if(ge.getCode().equals(PasswordMatches.class.getSimpleName())){
                    result.rejectValue("password", "Eqauls.pass", ge.getDefaultMessage());
                }
            }

            return "views/manage/adminUser/adminUser-update";
        }

        //저장
        adminUserService.save(saveAdminUser);

        // 검증 통과 시, 사용자 등록 로직 수행
        return "redirect:/manage/adminUser/list";
    }

    /**
     * @methodName : adminUserDelete
     * @date : 2023-05-15 오전 10:46
     * @author : 김재성
     * @Description: 관리자 삭제 submit
     **/
    @PostMapping("/adminUser/delete")
    public String adminUserDelete(@RequestParam Long id) {
        adminUserService.delete(id);
        // 검증 통과 시, 사용자 등록 로직 수행
        return "redirect:/manage/adminUser/list";
    }

    /**
    * @methodName :
    * @date : 2023-06-16 오후 11:55
    * @author : 김재성
    * @Description: 관리자 저장 dto
    **/
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @PasswordMatches
    public static class SaveAdminUser{
        private Long id;

        @NotBlank(message = "아이디를 입력해주세요.")
        @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.")
//        @duplicateCheckUserId
        private String userid;

        @NotBlank(message = "패스워드를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
        private String password;

        @NotBlank(message = "패스워드 확인을 입력해주세요.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
        private String confirmPassword;

        @NotBlank(message = "이름을 입력해주세요.")
        private String username;

        @JsonIgnore
        @Column(name = "activated")
        private boolean activated;

        private String delYn;               //삭제여부

        private LocalDateTime regDate;

        @Builder
        public SaveAdminUser(Long id, String userid, String username, String delYn){
            this.id = id;
            this.userid = userid;
            this.username = username;
            this.delYn = delYn;
        }

        public AdminUsers toEntity(){
            return AdminUsers.builder()
                    .id(id)
                    .userid(userid)
                    .password(password)
                    .username(username)
                    .delYn("N")
                    .activated(true)
                    .build();
        }
    }
}
