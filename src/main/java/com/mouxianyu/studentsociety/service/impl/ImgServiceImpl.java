package com.mouxianyu.studentsociety.service.impl;

import com.mouxianyu.studentsociety.common.enums.ObjTypeEnum;
import com.mouxianyu.studentsociety.common.util.FileUtil;
import com.mouxianyu.studentsociety.mapper.ImgMapper;
import com.mouxianyu.studentsociety.pojo.entity.Img;
import com.mouxianyu.studentsociety.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Service
public class ImgServiceImpl implements ImgService {
    @Autowired
    private ImgMapper imgMapper;

    @Override
    public Long add(MultipartFile multipartFile, String fileDir, Integer objType, Long objId) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String newFileName = FileUtil.upload(multipartFile, fileDir);
        Img img = new Img();
        img.setObjId(objId);
        img.setName(originalFilename);
        img.setUrl(fileDir + newFileName);
        img.setRelName(newFileName);
        img.setType(objType);
        imgMapper.insertSelective(img);
        return img.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        Img img = getById(id);
        if (img != null) {
            imgMapper.deleteByPrimaryKey(id);
            return FileUtil.delete(img.getUrl());
        }
        return false;
    }

    @Override
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            deleteById(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTypeAndObjId(Long objId, Integer type) {
        List<Img> imgs = queryByTypeObjId(objId, type);
        for (Img img : imgs) {
            FileUtil.delete(img.getUrl());
        }
        Example example = new Example(Img.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", type);
        criteria.andEqualTo("objId", objId);
        imgMapper.deleteByExample(example);
    }


    @Override
    public void updateById(Img img) {
        imgMapper.updateByPrimaryKeySelective(img);
    }

    @Override
    public Img getById(Long id) {
        return imgMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Img> queryByTypeObjId(Long objId, Integer objType) {
        Example example = new Example(Img.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", objType);
        criteria.andEqualTo("objId", objId);
        List<Img> imgs = imgMapper.selectByExample(example);
        return imgs;
    }
}
