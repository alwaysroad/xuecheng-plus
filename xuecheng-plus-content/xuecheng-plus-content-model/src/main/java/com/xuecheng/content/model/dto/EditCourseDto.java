package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cornelius
 * @date 2023/5/11 19:35
 * @description
 */

/**
 * 修改课程的dto，因为返回过来的模型数据跟添加课程的一致，所以可以
 * 继承，另外返回过来新增了id。新增是自增的，修改是要根据id来修改的
 */

@ApiModel(value="EditCourseDto", description="修改课程基本信息")
@Data
public class EditCourseDto extends  CourseBaseInfoDto{
    @ApiModelProperty(value = "课程id", required = true)
    private Long id;
}
