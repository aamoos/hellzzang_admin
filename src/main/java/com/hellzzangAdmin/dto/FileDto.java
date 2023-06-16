package com.hellzzangAdmin.dto;

import com.hellzzangAdmin.entity.FileInfo;
import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.hellzzangAdmin.dto
 * fileName       : FileDto
 * author         : 김재성
 * date           : 2023-05-11
 * description    : 파일 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-11        김재성       최초 생성
 */
@Data
public class FileDto {

    private Long id;                    //id

    private String originFileName;      //원본 파일명

    private String savedFileName;       //저장된 파일명

    private String uploadDir;           //경로명

    private String extension;           //확장자

    private Long size;                  //파일 사이즈

    private String contentType;         //ContentType

    private String delYn;               //삭제여부

    private String url;                 //썸네일 url

    public FileDto() {

    }

    @Builder
    public FileDto(Long id, String originFileName, String savedFileName, String uploadDir
            , String extension, Long size, String contentType, String delYn, String url) {
        this.id = id;
        this.originFileName = originFileName;
        this.savedFileName = savedFileName;
        this.uploadDir = uploadDir;
        this.extension = extension;
        this.size = size;
        this.contentType = contentType;
        this.delYn = delYn;
        this.url = url;
    }

    public FileInfo toEntity() {
        return FileInfo.builder()
                .originFileName(originFileName)
                .savedFileName(savedFileName)
                .uploadDir(uploadDir)
                .extension(extension)
                .size(size)
                .contentType(contentType)
                .delYn(delYn)
                .build();
    }
}
