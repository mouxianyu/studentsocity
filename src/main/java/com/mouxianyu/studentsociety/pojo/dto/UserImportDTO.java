package com.mouxianyu.studentsociety.pojo.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Getter
@Setter
public class UserImportDTO implements Serializable {
    private static final long serialVersionUID = 41546610974847685L;
    /**
     * 姓名
     */
    private String name;
    /**
     * 学号
     */
    private String no;
    /**
     * 年级
     */
    private String grade;
    /**
     * 学院
     */
    private String college;
    /**
     * 专业
     */
    private String major;
    /**
     * 性别
     */
    private String gender;
    /**
     * 生源地
     */
    private String origin;
    /**
     * 手机
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
}
