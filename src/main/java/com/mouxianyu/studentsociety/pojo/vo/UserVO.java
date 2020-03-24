package com.mouxianyu.studentsociety.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Getter
@Setter
public class UserVO implements Serializable {
    private static final long serialVersionUID = -8758969925869473778L;
    /**
     * 主键
     */
    private Long id;
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
    private Long college;
    /**
     * 专业
     */
    private String major;
    /**
     * 性别
     */
    private Boolean gender;
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
    /**
     * 密码
     */
    private String password;
    /**
     * 创建人id
     */
    private String createId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人id
     */
    private String modifyId;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 权限
     */
    private Integer authority;
    /**
     * 状态
     */
    private Integer status;
}
