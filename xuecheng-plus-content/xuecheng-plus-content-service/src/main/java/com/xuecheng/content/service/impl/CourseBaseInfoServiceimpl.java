package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.base.model.PagesParms;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cornelius
 * @date 2023/5/9 17:34
 */
@Service
public class CourseBaseInfoServiceimpl implements CourseBaseInfoService {

    @Resource
    CourseBaseMapper courseBaseMapper;
    @Override
    public PageResult<CourseBase> queryCourseBaseList(PagesParms pagesParms, QueryCourseParamsDto queryCourseParamsDto) {
        //构建查询条件对象
        LambdaQueryWrapper<CourseBase> query = new LambdaQueryWrapper<>();
        query.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()), CourseBase::getName,queryCourseParamsDto.getCourseName());
        //构建查询条件，根据课程审核状态查询
        query.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()), CourseBase::getAuditStatus,queryCourseParamsDto.getAuditStatus());
        //构建查询条件，根据课程发布状态查询
        query.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()), CourseBase::getStatus,queryCourseParamsDto.getPublishStatus());


        //分页对象
        Page<CourseBase> page = new Page<>(pagesParms.getPageNo(),pagesParms.getPageSize());
        //查询数据内容获得结果
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, query);
        //获取数据列表
        List<CourseBase> list = pageResult.getRecords();
        //获得数据总数
        long total = pageResult.getTotal();
        //构建结果集
        PageResult<CourseBase> courseBasePageResult = new PageResult<>(list, total, pagesParms.getPageNo(), pagesParms.getPageSize());

        return courseBasePageResult;
    }
}
