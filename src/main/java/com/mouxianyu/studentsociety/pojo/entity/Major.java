package com.mouxianyu.studentsociety.pojo.entity;

import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Table(name = "t_major")
@Getter
@Setter
public class Major implements Serializable {
    private static final long serialVersionUID = -5001627071747763807L;
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
     * 学院id
     */
    private Long collegeId;

    /**
     * 状态
     */
    private Integer status;
}
