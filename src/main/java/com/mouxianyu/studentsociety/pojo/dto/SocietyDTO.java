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
public class SocietyDTO extends PageDTO implements Serializable {
    private static final long serialVersionUID = -8085424501133056076L;
    /**
     * 主键
     */
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
