package com.mouxianyu.studentsociety.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Getter
@AllArgsConstructor
public enum  ActivityScaleEnum {
    /**
     * 超级管理员
     */
    SCHOOL(0,"校级"),
    /**
     * 普通用户
     */
    SOCIETY(1,"社团级"),
    ;

    /**
     * 编码
     */
    private int code;
    /**
     * 名称
     */
    private String name;
}
