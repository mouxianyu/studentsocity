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
@Table(name = "t_img")
@Getter
@Setter
public class Image implements Serializable {
    private static final long serialVersionUID = -6920607223359166892L;
    /**
     * 主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    /**
     * 图片名
     */
    private String name;

    /**
     * 图片地址
     */
    private String url;
}
