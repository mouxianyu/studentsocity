package com.mouxianyu.studentsociety.pojo.entity;

import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @description: 学院
 * @author: kingsme@yeah.net
 */
@Table(name = "t_college")
@Getter
@Setter
public class College implements Serializable {
    private static final long serialVersionUID = 4349287645728152612L;
    /**
     * 主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    /**
     * 学院名称
     */
    private String name;

    /**
     * 状态
     */
    private Integer status;
}
