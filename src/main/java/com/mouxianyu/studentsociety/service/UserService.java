package com.mouxianyu.studentsociety.service;

import com.mouxianyu.studentsociety.pojo.dto.UserDTO;
import com.mouxianyu.studentsociety.pojo.entity.User;
import com.mouxianyu.studentsociety.pojo.vo.UserVO;

import java.util.List;

/**
 * @description: 用户业务逻辑
 * @author: kingsme@yeah.net
 */
public interface UserService {

    /**
     * 通过id查询
     * @param id 主键
     * @return com.mouxianyu.studentsociety.pojo.entity.User
     */
    User getById(Long id);

    /**
     * 查询所有
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.entity.User>
     */
    List<User> queryAll();

    /**
     * 通过社团id查询
     * @param societyId
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.vo.UserVO>
     */
    List<UserVO> queryBySocietyId(Long societyId);

    /**
     * 分页查询
     * @param userDTO 查询条件
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.entity.User>
     */
    List<UserVO> queryByPage(UserDTO userDTO);

    /**
     * 根据条件查询条数
     * @param userDTO 条件
     * @return int
     */
    int getCountByCondition(UserDTO userDTO);

    /**
     * 根据id删除
     * @param id 主键
     */
    void deleteById(Long id);

    /**
     * 通过id更新
     * @param userDTO 更新
     */
    void updateById(UserDTO userDTO);

    /**
     * 根据id批量删除
     * @param ids
     */
    void deleteByIds(Long[] ids);

    /**
     * 新增
     * @param userDTO
     */
    void add(UserDTO userDTO);
}
