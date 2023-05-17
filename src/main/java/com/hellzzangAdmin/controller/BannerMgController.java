package com.hellzzangAdmin.controller;

import com.hellzzangAdmin.dto.AdminUsersDto;
import com.hellzzangAdmin.dto.BannerDto;
import com.hellzzangAdmin.dto.BannerFileDto;
import com.hellzzangAdmin.entity.AdminUsers;
import com.hellzzangAdmin.entity.Banner;
import com.hellzzangAdmin.entity.BannerFile;
import com.hellzzangAdmin.repository.BannerRepository;
import com.hellzzangAdmin.service.BannerService;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * packageName    : com.hellzzangAdmin.controller
 * fileName       : BannerMgController
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
public class BannerMgController {

    private final BannerService bannerService;
    private final BannerRepository bannerRepository;

    /**
     * @methodName : banner
     * @date : 2023-05-11 오전 10:46
     * @author : 김재성
     * @Description: 배너 리스트 화면
     **/
    @GetMapping("/banner/list")
    public String list(@PageableDefault Pageable pageable, Model model){
        Page<BannerDto> results = bannerService.selectBannerList(pageable);

        model.addAttribute("list", results);
        model.addAttribute("maxPage", 5);
        model.addAttribute("totalCount", results.getTotalElements());
        model.addAttribute("size",  results.getPageable().getPageSize());
        model.addAttribute("number",  results.getPageable().getPageNumber());

        return "views/manage/banner/banner-list";
    }

    /**
    * @methodName : write
    * @date : 2023-05-15 오후 1:21
    * @author : 김재성
    * @Description: 배너 등록화면
    **/
    @GetMapping("/banner/write")
    public String write(Model model){
        model.addAttribute("saveBanner", new SaveBanner());
        return "views/manage/banner/banner-write";
    }

    /**
    * @methodName : write
    * @date : 2023-05-16 오후 1:35
    * @author : 김재성
    * @Description: 배너 등록
    **/
    @PostMapping("/banner/write")
    public String write(@Valid SaveBanner saveBanner, BindingResult result, HttpServletRequest request){

        if (result.hasErrors()) {
            return "views/manage/banner/banner-write";
        }

        //해당 path로 등록이 되어있는지 확인
        List<Banner> list = bannerRepository.findByBannerPathAndDelYn(saveBanner.getBannerPath(), "N");

        //이미 등록이 있는경우
        if(list.size() != 0){
            result.rejectValue("bannerPath", "bannerPath.pass", "이미 등록된 배너입니다.");
            return "views/manage/banner/banner-write";
        }

        bannerService.save(saveBanner, request);
        return "redirect:/manage/banner/list";
    }

    /**
    * @methodName : update
    * @date : 2023-05-17 오후 1:32
    * @author : 김재성
    * @Description: 업데이트 화면
    **/
    @GetMapping("/banner/update/{id}")
    public String update(@PathVariable Long id, Model model){

        Banner banner = bannerService.find(id);

        //배너 조회
        SaveBanner saveBanner = SaveBanner.builder()
                .id(banner.getId())
                .bannerPath(banner.getBannerPath())
                .build();

        List<BannerFileDto> bannerFile = bannerService.findBannerFileList(id);

        model.addAttribute("saveBanner", saveBanner);
        model.addAttribute("bannerFile", bannerFile);

        return "views/manage/banner/banner-update";
    }

    /**
    * @methodName : update
    * @date : 2023-05-17 오후 1:32
    * @author : 김재성
    * @Description: 업데이트 화면
    **/
    @PostMapping("/banner/update/{id}")
    public String update(@Valid SaveBanner saveBanner, BindingResult result, HttpServletRequest request){

        if (result.hasErrors()) {
            return "views/manage/banner/banner-update";
        }

        //해당 path로 등록이 되어있는지 확인
        List<Banner> list = bannerRepository.findByBannerPathAndDelYn(saveBanner.getBannerPath(), "N");

        //이미 등록이 있는경우
        if(list.size() != 0){
            result.rejectValue("bannerPath", "bannerPath.pass", "이미 등록된 배너입니다.");
            return "views/manage/banner/banner-update";
        }

        bannerService.save(saveBanner, request);
        return "redirect:/manage/banner/list";
    }

    /**
    * @methodName : adminUserDelete
    * @date : 2023-05-17 오후 1:58
    * @author : 김재성
    * @Description: 배너삭제
    **/
    @PostMapping("/banner/delete")
    public String adminUserDelete(@RequestParam Long id) {
        bannerService.delete(id);
        // 검증 통과 시, 사용자 등록 로직 수행
        return "redirect:/manage/banner/list";
    }

    /**
    * @methodName :
    * @date : 2023-05-16 오후 12:57
    * @author : 김재성
    * @Description: 배너 저장 dto
    **/
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveBanner{

        private Long id;
        private String bannerPath;

        @Size(min=1, message = "배너이미지를 등록해주세요.")
        private List<Long> fileIdxList;

        @Builder
        public SaveBanner(Long id, String bannerPath){
            this.id = id;
            this.bannerPath = bannerPath;
        }

    }


}
