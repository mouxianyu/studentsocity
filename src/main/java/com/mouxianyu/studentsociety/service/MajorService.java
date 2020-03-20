package com.mouxianyu.studentsociety.service;

import com.mouxianyu.studentsociety.pojo.entity.Major;

import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
public interface MajorService {

    /**
     * 通过主键查询
     * @param id 主键
     * @return com.mouxianyu.studentsociety.pojo.entity.Major
     */
    Major getById(Long id);

    /**
     * 查询所有专业
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.entity.Major>
     */
    List<Major> queryAll();
}
