package com.mouxianyu.studentsociety.service;

import com.mouxianyu.studentsociety.pojo.dto.MajorDTO;
import com.mouxianyu.studentsociety.pojo.entity.Major;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    /**
     * 分页查询
     * @param majorDTO
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.entity.Major>
     */
    List<Major> queryByPage(MajorDTO majorDTO);

    /**
     * 按条件查询数量
     * @param majorDTO
     * @return int
     */
    int getCountByCondition(MajorDTO majorDTO);

    /**
     * 新增
     * @param majorDTO
     * @return java.lang.Long
     */
    Long add(MajorDTO majorDTO);

    /**
     * 通过id删除
     * @param id
     */
    void deleteById(Long id);

    /**
     * 通过id批量删除
     * @param ids
     */
    void deleteByIds(Long [] ids);

    /**
     * 根据id更新
     * @param id
     */
    void updateById(MajorDTO majorDTO);

    /**
     * 通过学院id查询
     * @param collegeId 学院id
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.entity.Major>
     */
    List<Major> queryByCollegeId(Long collegeId);

    /**
     * 导入专业信息
     * @param multipartFile
     * @param collegeName
     * @return java.lang.String
     */
    String upload(MultipartFile multipartFile, String collegeName, String majorName) throws Exception;

    /**
     * 通过姓名查询
     * @param name
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.entity.Major>
     */
    List<Major> queryByName(String name);

}
