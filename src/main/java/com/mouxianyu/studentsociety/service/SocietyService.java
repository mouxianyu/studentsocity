package com.mouxianyu.studentsociety.service;

import com.mouxianyu.studentsociety.pojo.dto.SocietyDTO;
import com.mouxianyu.studentsociety.pojo.entity.Society;
import com.mouxianyu.studentsociety.pojo.vo.SocietyVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
     * 通过id获得
     * @param id
     * @return com.mouxianyu.studentsociety.pojo.vo.SocietyVO
     */
    SocietyVO getByIdMore(Long id);

    /**
     * 分页查询
     * @param societyDTO 条件
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.vo.SocietyVO>
     */
    List<SocietyVO> queryByPage(SocietyDTO societyDTO);

    /**
     * 分页查询，包含图片
     * @param societyDTO
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.vo.SocietyVO>
     */
    List<SocietyVO> queryByPageWithImg(SocietyDTO societyDTO);

    /**
     * 查询所有
     * @param
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.entity.Society>
     */
    List<Society> queryAll();

    /**
     * 根据条件查询条数
     * @param societyDTO 条件
     * @return int
     */
    int getCountByCondition(SocietyDTO societyDTO);

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
     * 社团状态通统计
     * @param
     * @return java.util.Map<java.lang.String,java.util.List<java.lang.String>>
     */
    Map<String,List<String>> countBystatus();

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

    /**
     * 上传数据
     * @param societyName
     * @param multipartFile
     * @return java.lang.String
     */
    String upload(String societyName, MultipartFile multipartFile) throws IOException;

}
