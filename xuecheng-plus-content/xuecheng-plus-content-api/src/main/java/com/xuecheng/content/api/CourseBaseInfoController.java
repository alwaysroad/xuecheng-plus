package com.xuecheng.content.api;

import com.xuecheng.base.model.PageResult;
import com.xuecheng.base.model.PagesParms;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author cornelius
 * @date 2023/5/8 20:09
 */

@Api(tags = "课程信息编辑接口")
@RestController //相当于@Controller和@ResponseBody的结合 ，返回json格式的数据
public class CourseBaseInfoController {

    @Resource
    CourseBaseInfoService courseBaseInfoService;

    @PostMapping("/course/list")
    @ApiOperation("课程查询接口")
    public PageResult<CourseBase> list(PagesParms pagesParms, @RequestBody QueryCourseParamsDto queryCourseParamsDto){

        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(pagesParms, queryCourseParamsDto);
        return  courseBasePageResult;
    }

    @ApiOperation("新增课程")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated AddCourseDto addCourseDto){
        //获取到用户所属机构的id
        Long companyId = 1232141425L;
//        int i = 1/0;
        CourseBaseInfoDto courseBase = courseBaseInfoService.createCourseBase(companyId, addCourseDto);
        return courseBase;
    }

    @ApiOperation("根据id查询课程")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId) {

        return courseBaseInfoService.getCourseBaseInfo(courseId);

    }

    @ApiOperation("修改课程")
    @PutMapping("/course")
    public CourseBaseInfoDto modifyCourseBase(@RequestBody @Validated EditCourseDto editCourseDto){
        Long companyId = 1232141425L;

        return courseBaseInfoService.updateCourseBase(companyId, editCourseDto);
    }

}
