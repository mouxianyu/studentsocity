package com.mouxianyu.studentsociety.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Getter
@AllArgsConstructor
public enum  ScaleEnum {
    /**
     * 校级
     */
    SCHOOL_LEVEL(0,"校级"),
    /**
     * 院级
     */
    COLLEGE_LEVEL(1,"院级")
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
