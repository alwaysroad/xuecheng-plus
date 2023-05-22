package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Data;

import java.util.List;

/**
 * @author cornelius
 * @date 2023/5/15 18:53
 * @description 课程计划的dto
 */
@Data
public class TeachplanDto extends Teachplan {
    /**
     * 课程计划关联的媒资信息
     */
    TeachplanMedia teachplanMedia;

    /**
     * 子节点
     */
    List<TeachplanDto> teachPlanTreeNodes;
}
