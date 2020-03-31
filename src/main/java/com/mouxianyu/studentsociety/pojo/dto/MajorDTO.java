package com.mouxianyu.studentsociety.pojo.dto;

import com.mouxianyu.studentsociety.pojo.common.PageDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Getter
@Setter
public class MajorDTO extends PageDTO implements Serializable {
    private static final long serialVersionUID = 4542912253333733541L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 学院id
     */
    private Long collegeId;
}
