package com.mouxianyu.studentsociety.pojo.common;

import lombok.Getter;
import lombok.Setter;


/**
 * @description: 分页
 * @author: kingsme@yeah.net
 */
@Getter
@Setter
public class PageDTO {
    /**
     * 开始条数
     */
    public Integer start = 1;
    /**
     * 查询条数
     */
    public Integer row = 10;
}
