package com.xuecheng.media.model.dto;

import lombok.Data;

/**
 * @author cornelius
 * @date 2023/5/23 16:18
 * @description  上传普通文件请求参数
 */
@Data
public class UploadFileParamsDto {

    private String filename;
    private String fileType;
    private Long fileSize;
    private String tags;
    private String username;
    private String remark;
}
