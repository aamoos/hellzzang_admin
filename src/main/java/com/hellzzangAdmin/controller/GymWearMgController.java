package com.hellzzangAdmin.controller;

import com.hellzzangAdmin.dto.BannerDto;
import com.hellzzangAdmin.dto.GymWearDto;
import com.hellzzangAdmin.service.GymWearService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * packageName    : com.hellzzangAdmin.controller
 * fileName       : gymWearController
 * author         : 김재성
 * date           : 2023-05-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-19        김재성       최초 생성
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/manage")
public class GymWearMgController {

    private final GymWearService gymWearService;

    /**
     * @methodName : gymWear
     * @date : 2023-05-11 오전 10:47
     * @author : 김재성
     * @Description: 짐웨어 리스트
     **/
    @GetMapping("/gymWear/list")
    public String gymWear(@PageableDefault Pageable pageable, Model model){

        Page<GymWearDto> results = gymWearService.selectGymWearList(pageable);

        model.addAttribute("list", results);
        model.addAttribute("maxPage", 5);
        model.addAttribute("totalCount", results.getTotalElements());
        model.addAttribute("size",  results.getPageable().getPageSize());
        model.addAttribute("number",  results.getPageable().getPageNumber());

        return "views/manage/gymWear/gymWear-list";
    }

    /**
     * @methodName : gymWearSave
     * @date : 2023-05-11 오후 3:52
     * @author : 김재성
     * @Description: 짐웨어 등록화면
     **/
    @GetMapping("/gymWear/write")
    public String write(Model model){
        model.addAttribute("saveGymWear", new SaveGymWear());
        return "views/manage/gymWear/gymWear-write";
    }

    @PostMapping("/gymWear/write")
    public String write(@Valid SaveGymWear saveGymWear, BindingResult result, HttpServletRequest request){

        if (result.hasErrors()) {
            return "views/manage/gymWear/gymWear-write";
        }

        //해당 path로 등록이 되어있는지 확인
//        List<Banner> list = bannerRepository.findByBannerPathAndDelYn(saveBanner.getBannerPath(), "N");

        gymWearService.save(saveGymWear, request);

        return "redirect:/manage/gymWear/list";
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveGymWear{

        private Long id;

        private String title;

        @Lob
        @Column(name = "text_area", columnDefinition = "CLOB")
        private String contents;

        private Long thumbnailIdx;

        private List<Long> contentFileIdx;

        @Builder
        public SaveGymWear(Long id, String title, String contents, Long thumbnailIdx, List<Long> contentFileIdx){
            this.id = id;
            this.title = title;
            this.contents = contents;
            this.thumbnailIdx = thumbnailIdx;
            this.contentFileIdx = contentFileIdx;
        }

    }

}
