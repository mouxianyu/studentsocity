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
@Getter
@Setter
@Table(name = "t_img")
public class Img implements Serializable {
    private static final long serialVersionUID = 1884315723906853018L;
    /**
     * 主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    /**
     * 图片路径
     */
    private String url;
    /**
     * 图片名称
     */
    private String name;
    /**
     * 存储在本地的真实路径
     */
    private String relName;
    /**
     * 对象id
     */
    private Long objId;

    /**
     * 对象类型
     */
    private Integer type;
}
