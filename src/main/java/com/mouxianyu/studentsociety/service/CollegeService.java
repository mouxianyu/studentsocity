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

    /**
     * 通过id查询
     * @param id
     * @return com.mouxianyu.studentsociety.pojo.entity.College
     */
    College getById(Long id);

    /**
     * 批量添加
     * @param collegeList
     */
    void addList(List<College> collegeList);

    /**
     * 通过名称查询
     * @param name
     * @return com.mouxianyu.studentsociety.pojo.entity.College
     */
    List<College> queryByName(String name);
}
