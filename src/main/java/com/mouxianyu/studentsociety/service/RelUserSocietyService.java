package com.mouxianyu.studentsociety.service;

import com.mouxianyu.studentsociety.pojo.entity.RelUserSociety;

import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
public interface RelUserSocietyService {

    /**
     * 通过id查询
     * @param id
     * @return com.mouxianyu.studentsociety.pojo.entity.RelUserSociety
     */
    RelUserSociety getById(Long id);

    /**
     * 通过社团id查询
     * @param id
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.entity.RelUserSociety>
     */
    List<RelUserSociety> queryBySocietyIdAndStatus(Long id,Integer status);

    /**
     * 通过学生id查询
     * @param id
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.entity.RelUserSociety>
     */
    List<RelUserSociety> queryByUserId(Long id);

    /**
     * 通过用户id和社团id查询关系
     * @param userId
     * @param societyId
     * @return com.mouxianyu.studentsociety.pojo.entity.RelUserSociety
     */
    RelUserSociety getByUserIdAndSocietyId(Long userId, Long societyId);

    /**
     * 通过社团id和关系查询
     * @param societyId
     * @param relation
     * @return com.mouxianyu.studentsociety.pojo.entity.RelUserSociety
     */
    RelUserSociety getBySocietyIdAndRelation(Long societyId, Integer relation);

    /**
     * 新增关系
     * @param relUserSociety
     */
    void add(RelUserSociety relUserSociety);

    /**
     * 更新关系
     * @param relUserSociety
     */
    void updateById(RelUserSociety relUserSociety);

    /**
     * 删除关系
     * @param id
     */
    void deleteById(Long id);

    /**
     * 通过社团id删除关系
     * @param id
     */
    void deleteBySocietyId(Long id);

    /**
     * 查询社团人数
     * @param id
     * @return int
     */
    int countBySocietyId(Long id);

}
