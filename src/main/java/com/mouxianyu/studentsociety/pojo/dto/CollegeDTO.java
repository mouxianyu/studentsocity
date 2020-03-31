package com.mouxianyu.studentsociety.pojo.dto;

import com.mouxianyu.studentsociety.pojo.common.PageDTO;
import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Getter
@Setter
public class CollegeDTO extends PageDTO implements Serializable {
    private static final long serialVersionUID = -627926068303336379L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 学院名称
     */
    private String name;
}
