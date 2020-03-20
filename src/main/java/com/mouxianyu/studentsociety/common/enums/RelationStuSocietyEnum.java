package com.mouxianyu.studentsociety.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Getter
@AllArgsConstructor
public enum RelationStuSocietyEnum {
    /**
     * 社长
     */
    PRESIDENT(0,"社长"),

    VICE_PRESIDENT(1,"副社长"),

    MEMBER(2,"社员")
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
