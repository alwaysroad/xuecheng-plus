package com.xuecheng;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cornelius
 * @date 2023/5/10 13:34
 * @description
 */

@SpringBootTest
public class XuechengPlusContentServiceApplicationTests {
    @Resource
    CourseCategoryService courseCategoryService;



    @Test
    void contextCourseCategoryTest(){
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryService.queryTreeNodes("1");
        System.out.println("-------");
        System.out.println(courseCategoryTreeDtos);
    }
}
