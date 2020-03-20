package com.mouxianyu.studentsociety.service.impl;

import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.mapper.MajorMapper;
import com.mouxianyu.studentsociety.pojo.entity.Major;
import com.mouxianyu.studentsociety.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    private MajorMapper majorMapper;

    @Override
    public Major getById(Long id) {
        return majorMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Major> queryAll() {
        Example example = new Example(Major.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andNotEqualTo("status", StatusEnum.DELETED.getCode());
        return majorMapper.selectByExample(example);
    }
}
