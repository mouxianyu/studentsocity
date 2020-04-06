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
public class SocietyVO implements Serializable {

    private static final long serialVersionUID = -6140103580720714218L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 名称
     */
    private String name;

    /**
     * 社长id
     */
    private Long presidentId;

    /**
     * 社长名
     */
    private String presidentName;

    /**
     * 社长头像
     */
    private String presidentAvatar;

    /**
     * 副社长id
     */
    private Long vicePresidentId;

    /**
     * 副社长名
     */
    private String vicePresidentName;

    /**
     * 社团人数
     */
    private Integer userCount;

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

    /**
     * 与社团的关系状态
     */
    private Integer relationStatus;

    /**
     * 社团图像
     */
    private List<Img> imgs;
}
