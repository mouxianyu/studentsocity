package com.mouxianyu.studentsociety.service;

import com.mouxianyu.studentsociety.pojo.dto.SocietyDTO;
import com.mouxianyu.studentsociety.pojo.entity.Society;
import com.mouxianyu.studentsociety.pojo.vo.SocietyVO;

import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
public interface SocietyService {
    /**
     * 通过id查询
     * @param id 主键
     * @return com.mouxianyu.studentsociety.pojo.entity.Society
     */
    Society getById(Long id);

    /**
     * 分页查询
     * @param societyDTO 条件
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.vo.SocietyVO>
     */
    List<SocietyVO> queryByPage(SocietyDTO societyDTO);

    /**
     * 根据条件查询条数
     * @param societyDTO 条件
     * @return int
     */
    public int getCountByCondition(SocietyDTO societyDTO);

    /**
     * 新建
     * @param societyDTO 内容
     */
    Long add(SocietyDTO societyDTO);

    /**
     * 通过主键删除
     * @param id 主键
     */
    void deleteById(Long id);

    /**
     * 通过主键批量删除
     * @param ids 主键
     */
    void deleteByIds(Long[] ids);

    /**
     * 通过id更新
     * @param societyDTO 内容
     */
    void updateById(SocietyDTO societyDTO);

    /**
     * 学院统计
     * @param societyId
     * @return java.util.Map<java.util.List<java.lang.Integer>,java.util.List<java.lang.String>>
            */
    Map<String,List<String>> countByCollege(Long societyId);

    /**
     * 专业统计
     * @param societyId
     * @return java.util.Map<java.lang.String,java.util.List<java.lang.String>>
     */
    Map<String,List<String>> countByMajor(Long societyId);

    /**
     * 年级统计
     * @param societyId
     * @return java.util.Map<java.lang.String,java.util.List<java.lang.String>>
     */
    Map<String,List<String>> countByGrade(Long societyId);

    /**
     * 性别统计
     * @param societyId
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    Map<String,List<String>> countByGender(Long societyId);


}
