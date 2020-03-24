package com.mouxianyu.studentsociety.service;

import com.mouxianyu.studentsociety.pojo.entity.College;

import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
public interface CollegeService {
    /**
     * 查询所有学院
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.entity.College>
     */
    List<College> queryAll();
}
