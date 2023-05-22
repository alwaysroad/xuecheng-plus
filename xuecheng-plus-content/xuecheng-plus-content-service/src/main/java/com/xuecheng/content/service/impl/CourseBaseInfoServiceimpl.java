package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.base.model.PagesParms;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author cornelius
 * @date 2023/5/9 17:34
 */
@Service
public class CourseBaseInfoServiceimpl implements CourseBaseInfoService {

    @Resource
    CourseBaseMapper courseBaseMapper;
    @Resource
    CourseMarketMapper courseMarketMapper;
    @Resource
    CourseCategoryMapper courseCategoryMapper;
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

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {


      /*  //参数的合法性校验
        if (StringUtils.isBlank(dto.getName())) {
           throw new RuntimeException("课程名称为空");
          //  XueChengPlusException.cast("课程名称为空");
        }

        if (StringUtils.isBlank(dto.getMt())) {
            throw new RuntimeException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getSt())) {
            throw new RuntimeException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getGrade())) {
            throw new RuntimeException("课程等级为空");
        }

        if (StringUtils.isBlank(dto.getTeachmode())) {
            throw new RuntimeException("教育模式为空");
        }

        if (StringUtils.isBlank(dto.getUsers())) {
            throw new RuntimeException("适应人群为空");
        }

        if (StringUtils.isBlank(dto.getCharge())) {
            throw new RuntimeException("收费规则为空");
        }
*/


        //将课程信息表course_base写入数据
        CourseBase courseBaseNew = new CourseBase();
        //将填写在表单的信息赋值给新对象
        BeanUtils.copyProperties(dto,courseBaseNew);
        //拷贝后会将新对象中的值完全替代，如果addCourseDto里有空，那么新对象也会为空,所以防止为空，后设置
        courseBaseNew.setCompanyId(companyId);
        //对于表单中没有但是数据库中存在的信息自行设置
        courseBaseNew.setAuditStatus("202002");
        courseBaseNew.setStatus("203001");
        courseBaseNew.setCreateDate(LocalDateTime.now());
        int insert = courseBaseMapper.insert(courseBaseNew);
        if(insert<=0){
            //System.out.println("=====");
            throw new RuntimeException("课程插入失败");
        }

        //向课程营销表course_market写入数据
        CourseMarket courseMarketNew = new CourseMarket();
        //将页面输入的数据拷贝到coursebaseNew
        BeanUtils.copyProperties(dto,courseMarketNew);
        //课程id
        Long courseId = courseBaseNew.getId();
        courseMarketNew.setId(courseId);
        //处理是否收费的情况来插入数据
        saveCourseMarketNew(courseMarketNew);
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfo(courseId);
        return courseBaseInfo;
    }

    /**
     *
     * @param courseId 课程的id
     * @return 课程的总信息，返回给接口
     */

    public CourseBaseInfoDto getCourseBaseInfo(Long courseId) {
        //从课程基本信息表查询
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            return null;
        }
        //从营销表查询
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        //组装在一起
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);
        if (courseMarket != null) {
            BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        }
        //得到分类信息插入数据,首先根据小分类的id得到课程分类的对象
      /*  String st = courseBase.getSt();
        System.out.println(st);
        CourseBase courseBase1 = courseBaseMapper.selectById(st);
        System.out.println(courseBase1);*/
        CourseCategory selectByIdSt = courseCategoryMapper.selectById(courseBaseInfoDto.getSt());
        //设置小分类名称
        courseBaseInfoDto.setSt(selectByIdSt.getName());
        //根据大分类id得到对象
        CourseCategory selectByIdMt = courseCategoryMapper.selectById(courseBaseInfoDto.getMt());
        courseBaseInfoDto.setMt(selectByIdMt.getName());
        return courseBaseInfoDto;
    }

    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto) {
        //得到课程ID
        Long courseId = editCourseDto.getId();
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            XueChengPlusException.cast("课程不存在");
        }
        //检验本机构只能修改本机构的课程
        if (!courseBase.getCompanyId() .equals(companyId)) {
            XueChengPlusException.cast("没有权限修改其他机构的课程");
        }
        //封装基本的信息
        BeanUtils.copyProperties(editCourseDto,courseBase);
        courseBase.setChangeDate(LocalDateTime.now());
        //更新到数据库
        courseBaseMapper.updateById(courseBase);
        //更新营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        BeanUtils.copyProperties(editCourseDto,courseMarket);
        courseMarket.setId(courseId);
        saveCourseMarketNew(courseMarket);
        CourseBaseInfoDto courseBaseInfo = this.getCourseBaseInfo(courseId);
        return courseBaseInfo;
    }


    /**
     *
     * @param courseMarketNew 营销信息
     * @return存在则更新，不存在就不更新
     */

    private int saveCourseMarketNew(CourseMarket courseMarketNew) {
        //参数合法性检验
        String charge = courseMarketNew.getCharge();
        if (StringUtils.isEmpty(charge) ) {
            throw new RuntimeException("收费规则为空");
        }
        //如果该课程收费，价格没有填写也抛出异常
        if (charge.equals("201001")) {
            if (courseMarketNew.getPrice()==null||courseMarketNew.getPrice().floatValue()<=0) {
                throw new RuntimeException("课程价格不能为空且必须大于0");
            }
        }
        //从数据库查询营销信息，存在则更新、不存在则添加
        Long id = courseMarketNew.getId();//得到主键id
        //从数据库查
        CourseMarket courseMarket = courseMarketMapper.selectById(id);
        if (courseMarket == null) {
            //插入数据库信息
            int insert = courseMarketMapper.insert(courseMarketNew);
            return insert;
        }else {
            //更新，将courseMarketNew拷贝到coursemarket
            BeanUtils.copyProperties(courseMarketNew,courseMarket);
            courseMarket.setId(courseMarketNew.getId());
            int i = courseMarketMapper.updateById(courseMarket);
            return i;
        }
    }
}
