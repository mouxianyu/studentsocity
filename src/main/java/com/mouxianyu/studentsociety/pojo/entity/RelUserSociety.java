package com.mouxianyu.studentsociety.pojo.entity;

import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: 社团与学生关系表
 * @author: kingsme@yeah.net
 */@Table(name = "t_rel_user_society")
@Getter
@Setter
public class RelUserSociety implements Serializable {
    private static final long serialVersionUID = -3176684296559078733L;

    /**
     * 主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    /**
     * 学生id
     */
    private Long userId;

    /**
     * 社团id
     */
    private Long societyId;

    /**
     * 关系
     */
    private Integer relation;

    /**
     * 关系建立时间（通过社团审批的时间）
     */
    private Date estTime;

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
     * 状态
     */
    private Integer status;
}
