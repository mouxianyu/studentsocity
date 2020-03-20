package com.mouxianyu.studentsociety.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Getter
@AllArgsConstructor
public enum AuthTypeEnum {
    /**
     * 超级管理员
     */
    ADMIN(0,"超级管理员"),
    /**
     * 普通用户
     */
    USER(1,"普通用户")
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
