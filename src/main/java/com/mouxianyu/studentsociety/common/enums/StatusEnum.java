package com.mouxianyu.studentsociety.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {
    /**
     * 正常
     */
    NORMAL(0,"正常"),

    /**
     * 失效
     */
    INVALID(1,"失效"),

    /**
     * 删除
     */
    DELETED(2,"删除"),

    /**
     * 审核中
     */
    AUDITING(3,"审核中")
    ;

    /**
     * 编码
     */
    private int code;
    /**
     * 名称
     */
    private String name;

    public static String getNameByCode(int code){
        StatusEnum[] statusEnum = StatusEnum.values();
        for (int i = 0 ;i<StatusEnum.values().length;i++){
            if(code==statusEnum[i].getCode()){
                return statusEnum[i].getName();
            }
        }
        return null;
    }
}
