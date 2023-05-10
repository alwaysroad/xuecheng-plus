package com.xuecheng.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author cornelius
 * @date 2023/5/8 19:46
 * @description 分页查询的参数
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PagesParms {

    //当前页码
    @ApiModelProperty("当前页码")
    private Long pageNo = 1L;
    //每页显示的记录数
    @ApiModelProperty("每页记录数")
    private  Long pageSize = 30L;
}
