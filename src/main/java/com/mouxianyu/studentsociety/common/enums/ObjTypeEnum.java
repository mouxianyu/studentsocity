package com.mouxianyu.studentsociety.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Getter
@AllArgsConstructor
public enum ObjTypeEnum {
    /**
     * 活动
     */
    ACTIVITY(0,"活动"),
    /**
     * 社团
     */
    SOCIETY(1,"社团"),

    /**
     * 头像
     */
    AVATAR(2,"头像"),

    /**
     * 新闻
     */
    NEWS(3,"新闻")
    ;

    /**
     * 编码
     */
    private int code;
    /**
     * 名称
     */
    private String name;

    public static ObjTypeEnum getByCode(int code){
        for (ObjTypeEnum objTypeEnum:values()){
            if(objTypeEnum.getCode()==code){
                return objTypeEnum;
            }
        }
        return null;
    }
}
