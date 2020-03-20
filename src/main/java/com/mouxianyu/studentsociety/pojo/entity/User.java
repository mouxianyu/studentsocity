package com.mouxianyu.studentsociety.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: 用户
 * @author: kingsme@yeah.net
 */
@Table(name = "t_user")
@Getter
@Setter
public class User implements Serializable {
    private static final long serialVersionUID = 7509831965814277358L;
    /**
     * 主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
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
     * 专业
     */
    private Long major;
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
    @JsonIgnore
    private String password;
    /**
     * 创建人id
     */
    private Long createId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人id
     */
    private Long modifyId;
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
