package com.mouxianyu.studentsociety.pojo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: 社团
 * @author: kingsme@yeah.net
 */
@Table(name = "t_society")
@Getter
@Setter
public class Society implements Serializable {
    private static final long serialVersionUID = -3550187562306852469L;
    /**
     * 主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 详情
     */
    private String detail;
    /**
     * 规模
     */
    private Integer scale;
    /**
     * 成立时间（审核通过时间）
     */
    private Date estTime;
    /**
     * 创建人
     */
    private Long createId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
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
