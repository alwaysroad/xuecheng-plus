package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * @author cornelius
 * @date 2023/5/20 14:30
 * @description
 */
public interface TeachplanService {

    public List<TeachplanDto> findTeachplanTree(long courseId);

    /**
     *
     * @param teachplanDto 课程计划信息
     */
    public void saveTeachplan(SaveTeachplanDto teachplanDto);
}
