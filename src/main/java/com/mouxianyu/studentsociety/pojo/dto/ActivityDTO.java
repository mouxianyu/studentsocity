package com.mouxianyu.studentsociety.pojo.dto;

import com.mouxianyu.studentsociety.pojo.common.PageDTO;
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
public class ActivityDTO extends PageDTO implements Serializable {
    private static final long serialVersionUID = -9163550741326860502L;
    /**
     * 主键
     */
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
