package com.mouxianyu.studentsociety.pojo.vo;

import com.mouxianyu.studentsociety.pojo.entity.Img;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Getter
@Setter
public class ActivityVO implements Serializable {
    private static final long serialVersionUID = -8069961375980479750L;
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
     * 社团名
     */
    private String societyName;

    /**
     * 活动审批通过时间
     */
    private Date estTime;

    /**
     * 创建人
     */
    private Long createId;

    /**
     * 申请人姓名
     */
    private String createName;

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

    /**
     * 活动包含图片
     */
    private List<Img> imgs;
}
