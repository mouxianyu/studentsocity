package com.mouxianyu.studentsociety.service;

import com.mouxianyu.studentsociety.pojo.entity.Img;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */

public interface ImgService {
    /**
     * 添加
     * @return java.lang.Long
     */
    Img add(MultipartFile multipartFiles, String fileDir, Integer objType, Long objId) throws IOException;

    /**
     * 根据id删除
     * @param id
     */
    boolean deleteById(Long id);

    /**
     * 根据id删除
     * @param ids
     * @return void
     */
    void deleteByIds(Long [] ids);

    /**
     * 根据id和类型删除
     * @param objId
     * @param type
     */
    void deleteByTypeAndObjId(Long objId,Integer type);

    /**
     * 根据id更新
     * @return void
     */
    Img updateById(MultipartFile multipartFile, String fileDir,Long id) throws IOException;

    /**
     * 根据id查询
     * @param id
     * @return com.mouxianyu.studentsociety.pojo.entity.Img
     */
    Img getById(Long id);

    /**
     * 根据对象id查询
     * @param objId
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.entity.Img>
     */
    List<Img> queryByTypeObjId(Long objId, Integer objType);
}
