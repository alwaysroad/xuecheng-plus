package com.xuecheng.content.service;

import com.xuecheng.base.model.PageResult;
import com.xuecheng.base.model.PagesParms;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

/**
 * @author cornelius
 * @date 2023/5/9 17:20
 */
public interface CourseBaseInfoService{


    /**
     * 课程分类查询
     * @param pagesParms  分页查询参数
     * @param queryCourseParamsDto 分页查询的条件
     * @return 查询结果
     */
    public PageResult<CourseBase> queryCourseBaseList(PagesParms pagesParms, QueryCourseParamsDto queryCourseParamsDto);

    /**
     * 新增课程
     * @param companyId 机构id
     * @param addCourseDto 课程信息
     * @return 详细的课程信息
     */
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);
}
