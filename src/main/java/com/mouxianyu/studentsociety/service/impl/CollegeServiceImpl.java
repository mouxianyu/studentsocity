package com.mouxianyu.studentsociety.service.impl;

import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.mapper.CollegeMapper;
import com.mouxianyu.studentsociety.pojo.dto.CollegeDTO;
import com.mouxianyu.studentsociety.pojo.entity.College;
import com.mouxianyu.studentsociety.pojo.entity.Major;
import com.mouxianyu.studentsociety.service.CollegeService;
import com.mouxianyu.studentsociety.service.MajorService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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

    @Autowired
    private MajorService majorService;

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

    @Override
    public List<College> queryByPage(CollegeDTO collegeDTO) {
        Example example = condition(collegeDTO);
        RowBounds rowBounds = new RowBounds(collegeDTO.getStart(), collegeDTO.getRow());
        return collegeMapper.selectByExampleAndRowBounds(example,rowBounds);
    }

    @Override
    public void updateById(CollegeDTO collegeDTO) {
        College college = new College();
        BeanUtils.copyProperties(collegeDTO,college);
        collegeMapper.updateByPrimaryKeySelective(college);
    }

    @Override
    public Long add(CollegeDTO collegeDTO) {
        College college = new College();
        BeanUtils.copyProperties(collegeDTO,college);
        college.setStatus(StatusEnum.NORMAL.getCode());
        collegeMapper.insertSelective(college);
        return college.getId();
    }

    @Override
    public int getCountByCondition(CollegeDTO collegeDTO) {
        Example example = condition(collegeDTO);
        int i = collegeMapper.selectCountByExample(example);
        return collegeMapper.selectCountByExample(example);
    }

    @Override
    public void deleteById(Long id) {
        List<Major> majorList = majorService.queryByCollegeId(id);
        for (Major major : majorList) {
            majorService.deleteById(major.getId());
        }
        collegeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            deleteById(id);
        }
    }

    private Example condition(CollegeDTO collegeDTO){
        Example example = new Example(College.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("id").desc();
        criteria.andEqualTo("status",StatusEnum.NORMAL.getCode());
        if(!StringUtils.isEmpty(collegeDTO.getName())){
            criteria.andLike("name", "%" + collegeDTO.getName() + "%");
        }
        return example;
    }
}
