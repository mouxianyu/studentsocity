package com.mouxianyu.studentsociety.service.impl;

import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.mapper.CollegeMapper;
import com.mouxianyu.studentsociety.pojo.entity.College;
import com.mouxianyu.studentsociety.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Service
public class CollegeServiceImpl implements CollegeService {

    @Autowired
    private CollegeMapper collegeMapper;

    @Override
    public List<College> queryAll() {
        Example example = new Example(College.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andNotEqualTo("status", StatusEnum.DELETED.getCode());
        return collegeMapper.selectByExample(example);
    }

    @Override
    public College getById(Long id) {
        return collegeMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addList(List<College> collegeList) {
        for (College college : collegeList) {
            collegeMapper.insertSelective(college);
        }
    }

    @Override
    public List<College> queryByName(String name) {
        Example example = new Example(College.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name",name);
        criteria.andEqualTo("status",StatusEnum.NORMAL.getCode());
        return collegeMapper.selectByExample(example);
    }
}
