package com.mouxianyu.studentsociety.service;

import com.mouxianyu.studentsociety.pojo.dto.ActivityDTO;
import com.mouxianyu.studentsociety.pojo.entity.Activity;
import com.mouxianyu.studentsociety.pojo.vo.ActivityVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
public interface ActivityService {
    /**
     * 分页查询
     *
     * @param activityDTO
     * @return
     */
    List<ActivityVO> queryByPage(ActivityDTO activityDTO);

    /**
     * 分页查询，包含图片
     * @param activityDTO
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.vo.ActivityVO>
     */
    List<ActivityVO> queryByPageWithImg(ActivityDTO activityDTO);

    /**
     * 根据社团id查询
     * @param id
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.vo.ActivityVO>
     */
    List<ActivityVO> queryBySocietyId(Long id);

    /**
     * 按条件查询条数
     *
     * @param activityDTO
     * @return int
     */
    int getCountByCondition(ActivityDTO activityDTO);

    /**
     * 根据id查询
     *
     * @param id
     * @return com.mouxianyu.studentsociety.pojo.entity.Activity
     */
    Activity getById(Long id);

    /**
     * 根据id查询
     *
     * @param id
     * @return com.mouxianyu.studentsociety.pojo.vo.ActivityVO
     */
    ActivityVO getByIdMore(Long id);

    /**
     * 通过id查询
     *
     * @param id
     * @return void
     */
    void deleteById(Long id);

    /**
     * 批量根据id删除
     *
     * @param ids
     * @return void
     */
    void deleteByIds(Long[] ids);

    /**
     * 根据id修改
     *
     * @param activity
     * @return void
     */
    void updateById(Activity activity, MultipartFile[] multipartFiles) throws IOException;

    /**
     * 新增
     *
     * @param activity
     * @return java.lang.Long
     */
    Long add(Activity activity, MultipartFile[] multipartFiles) throws IOException;
}
