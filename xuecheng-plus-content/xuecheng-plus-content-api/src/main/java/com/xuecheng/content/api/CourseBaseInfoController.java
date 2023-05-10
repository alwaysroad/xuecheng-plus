package com.xuecheng.content.api;

import com.xuecheng.base.model.PageResult;
import com.xuecheng.base.model.PagesParms;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
