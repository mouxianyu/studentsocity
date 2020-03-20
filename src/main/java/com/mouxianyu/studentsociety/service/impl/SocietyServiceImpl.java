package com.mouxianyu.studentsociety.service.impl;

import com.mouxianyu.studentsociety.common.enums.ScaleEnum;
import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.mapper.SocietyMapper;
import com.mouxianyu.studentsociety.pojo.dto.SocietyDTO;
import com.mouxianyu.studentsociety.pojo.entity.Society;
import com.mouxianyu.studentsociety.pojo.vo.SocietyVO;
import com.mouxianyu.studentsociety.service.SocietyService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Service
public class SocietyServiceImpl implements SocietyService {
    @Autowired
    private SocietyMapper societyMapper;

    @Override
    public Society getById(Long id) {
        return societyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SocietyVO> queryByPage(SocietyDTO societyDTO) {
        Example example = condition(societyDTO);
        RowBounds rowBounds = new RowBounds(societyDTO.getStart(),societyDTO.getRow());
        List<SocietyVO> societyVOS = new ArrayList<>();
        List<Society> societies = societyMapper.selectByExampleAndRowBounds(example, rowBounds);
        for (Society society : societies) {
            SocietyVO societyVO = new SocietyVO();
            BeanUtils.copyProperties(society,societyVO);
            societyVOS.add(societyVO);
        }
        return societyVOS;
    }

    @Override
    public int getCountByCondition(SocietyDTO societyDTO) {
        Example example = condition(societyDTO);
        return societyMapper.selectCountByExample(example);
    }

    @Override
    public void add(SocietyDTO societyDTO) {
        Society society = new Society();
        BeanUtils.copyProperties(societyDTO,society);
        society.setCreateTime(new Date());
        if(society.getStatus()==null){
            society.setStatus(StatusEnum.INVALID.getCode());
        }
        if(society.getScale()==null){
            society.setStatus(ScaleEnum.SCHOOL_LEVEL.getCode());
        }
        societyMapper.insertSelective(society);
    }

    @Override
    public void deleteById(Long id) {
        Society society = new Society();
        society.setId(id);
        society.setStatus(StatusEnum.DELETED.getCode());
        societyMapper.updateByPrimaryKeySelective(society);
    }

    @Override
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void updateById(SocietyDTO societyDTO) {
        Society society = new Society();
        BeanUtils.copyProperties(societyDTO,society);
        society.setModifyTime(new Date());
        societyMapper.updateByPrimaryKeySelective(society);
    }

    private Example condition(SocietyDTO societyDTO){
        Example example = new Example(Society.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("id").desc();
        if (!StringUtils.isEmpty(societyDTO.getName())) {
            criteria.andLike("name", "%" + societyDTO.getName() + "%");
        }
        criteria.andNotEqualTo("status", StatusEnum.DELETED.getCode());
        return example;
    }
}
