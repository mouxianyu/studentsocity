package com.mouxianyu.studentsociety.service;

import com.mouxianyu.studentsociety.pojo.dto.CollegeDTO;
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

    /**
     * 分页查询
     * @param collegeDTO
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.entity.College>
     */
    List<College> queryByPage(CollegeDTO collegeDTO);

    /**
     * 更新
     * @param collegeDTO
     */
    void updateById(CollegeDTO collegeDTO);

    /**
     * 新增
     * @param collegeDTO
     * @return java.lang.Long
     */
    Long add(CollegeDTO collegeDTO);

    /**
     * TODO
     * @param
     * @return int
     */
    int getCountByCondition(CollegeDTO collegeDTO);

    /**
     * 根据id删除
     * @param id
     * @return void
     */
    void deleteById(Long id);

    /**
     * 根据多个id删除
     * @param ids
     * @return void
     */
    void deleteByIds(Long[] ids);
}
