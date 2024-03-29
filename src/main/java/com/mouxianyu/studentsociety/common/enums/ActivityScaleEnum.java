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
     * 校级
     */
    SCHOOL(0,"校级"),
    /**
     * 社团级
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
