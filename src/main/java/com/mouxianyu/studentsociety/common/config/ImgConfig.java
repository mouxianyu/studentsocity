package com.mouxianyu.studentsociety.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Component
@ConfigurationProperties(prefix = "mouxianyu.img-path")
@Getter
@Setter
public class ImgConfig {
    /**
     * 活动图片路径
     */
    String activity;
    /**
     * 社团图片路径
     */
    String society;
    /**
     * 头像路径
     */
    String avatar;
    /**
     * 新闻图片路径
     */
    String news;
}
