package com.xuecheng.content.service;

import com.xuecheng.base.model.PageResult;
import com.xuecheng.base.model.PagesParms;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

/**
 * @author cornelius
 * @date 2023/5/9 17:20
 */
public interface CourseBaseInfoService{

    public PageResult<CourseBase> queryCourseBaseList(PagesParms pagesParms, QueryCourseParamsDto queryCourseParamsDto);
}
