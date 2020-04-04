package com.mouxianyu.studentsociety.pojo.entity;

import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Table(name = "t_activity")
@Getter
@Setter
public class Activity implements Serializable {
    private static final long serialVersionUID = 5870442512418457194L;

    /**
     * 主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    /**
     * 活动标题
     */
    private String title;

    /**
     * 活动内容
     */
    private String content;

    /**
     * 活动规模
     */
    private Integer scale;

    /**
     * 举办社团id
     */
    private Long societyId;

    /**
     * 活动审批通过时间
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
