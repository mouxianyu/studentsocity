package com.mouxianyu.studentsociety.service;

import com.mouxianyu.studentsociety.pojo.dto.SocietyDTO;
import com.mouxianyu.studentsociety.pojo.dto.UserDTO;
import com.mouxianyu.studentsociety.pojo.dto.UserImportDTO;
import com.mouxianyu.studentsociety.pojo.entity.Img;
import com.mouxianyu.studentsociety.pojo.entity.User;
import com.mouxianyu.studentsociety.pojo.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * @description: 用户业务逻辑
 * @author: kingsme@yeah.net
 */
public interface UserService {

    /**
     * 通过id查询
     *
     * @param id 主键
     * @return com.mouxianyu.studentsociety.pojo.entity.User
     */
    User getById(Long id);

    /**
     * 通过学号活动
     * @param no
     * @return com.mouxianyu.studentsociety.pojo.entity.User
     */
    User getByNo(String no);


    /**
     * 查询所有
     *
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.entity.User>
     */
    List<User> queryAll(UserDTO userDTO);

    /**
     * 通过社团id查询
     *
     * @param societyDTO
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.vo.UserVO>
     */
    List<UserVO> queryBySocietyIdAndCondition(SocietyDTO societyDTO);

    /**
     * 分页查询
     *
     * @param userDTO 查询条件
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.entity.User>
     */
    List<UserVO> queryByPage(UserDTO userDTO);

    /**
     * 根据条件查询条数
     *
     * @param userDTO 条件
     * @return int
     */
    int getCountByCondition(UserDTO userDTO);

    /**
     * 社团状态通统计
     * @param
     * @return java.util.Map<java.lang.String,java.util.List<java.lang.String>>
     */
    Map<String,List<String>> countBystatus();

    /**
     * 根据id删除
     *
     * @param id 主键
     */
    void deleteById(Long id);

    /**
     * 通过id更新
     *
     * @param userDTO 更新
     */
    void updateById(UserDTO userDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * 根据id批量删除
     *
     * @param ids
     */
    void deleteByIds(Long[] ids);

    /**
     * 新增
     *
     * @param userDTO
     */
    void add(UserDTO userDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * 导入上传数据
     *
     * @param userImportDTO
     * @param multipartFile
     * @return java.lang.String
     */
    String upload(UserImportDTO userImportDTO, MultipartFile multipartFile) throws IOException;

}
