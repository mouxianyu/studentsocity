package com.mouxianyu.studentsociety.service.impl;

import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.mapper.RelUserSocietyMapper;
import com.mouxianyu.studentsociety.pojo.entity.RelUserSociety;
import com.mouxianyu.studentsociety.service.RelUserSocietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Service
public class RelUserSocietyServiceImpl implements RelUserSocietyService {

    @Autowired
    private RelUserSocietyMapper relUserSocietyMapper;

    @Override
    public RelUserSociety getById(Long id) {
        return relUserSocietyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<RelUserSociety> queryBySocietyId(Long id) {
        Example example = new Example(RelUserSociety.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("societyId",id);
        criteria.andEqualTo("status",StatusEnum.NORMAL.getCode());
        return relUserSocietyMapper.selectByExample(example);
    }

    @Override
    public List<RelUserSociety> queryByUserId(Long id) {
        Example example = new Example(RelUserSociety.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",id);
        criteria.andNotEqualTo("status",StatusEnum.DELETED.getCode());
        return relUserSocietyMapper.selectByExample(example);
    }

    @Override
    public RelUserSociety getByUserIdAndSocietyId(Long userId, Long societyId) {
        Example example = new Example(RelUserSociety.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("societyId",societyId);
        criteria.andNotEqualTo("status",StatusEnum.DELETED.getCode());
        return relUserSocietyMapper.selectOneByExample(example);
    }

    @Override
    public RelUserSociety getBySocietyIdAndRelation(Long societyId, Integer relation) {
        Example example = new Example(RelUserSociety.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("societyId",societyId);
        criteria.andEqualTo("relation",relation);
        criteria.andNotEqualTo("status",StatusEnum.DELETED.getCode());
        return relUserSocietyMapper.selectOneByExample(example);
    }

    @Override
    public void add(RelUserSociety relUserSociety) {
        relUserSociety.setStatus(StatusEnum.AUDITING.getCode());
        relUserSociety.setCreateTime(new Date());
        relUserSocietyMapper.insert(relUserSociety);
    }

    @Override
    public void updateById(RelUserSociety relUserSociety) {
        relUserSociety.setModifyTime(new Date());
        relUserSocietyMapper.updateByPrimaryKeySelective(relUserSociety);
    }

    @Override
    public void deleteById(Long id) {
        RelUserSociety relUserSociety = new RelUserSociety();
        relUserSociety.setId(id);
        relUserSociety.setStatus(StatusEnum.DELETED.getCode());
        relUserSocietyMapper.updateByPrimaryKeySelective(relUserSociety);
    }

    @Override
    public void deleteBySocietyId(Long id) {
        Example example = new Example(RelUserSociety.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("societyId",id);
        RelUserSociety relUserSociety = new RelUserSociety();
        relUserSociety.setStatus(StatusEnum.DELETED.getCode());
        relUserSocietyMapper.updateByExampleSelective(relUserSociety,example);
    }

    @Override
    public int countBySocietyId(Long id) {
        Example example = new Example(RelUserSociety.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("societyId",id);
        criteria.andEqualTo("status",StatusEnum.NORMAL.getCode());
        return relUserSocietyMapper.selectCountByExample(example);
    }
}
