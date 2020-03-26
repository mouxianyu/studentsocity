package com.mouxianyu.studentsociety.service.impl;

import com.mouxianyu.studentsociety.common.enums.ScaleEnum;
import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.common.enums.UserSocietyRelationEnum;
import com.mouxianyu.studentsociety.mapper.SocietyMapper;
import com.mouxianyu.studentsociety.pojo.dto.SocietyDTO;
import com.mouxianyu.studentsociety.pojo.entity.*;
import com.mouxianyu.studentsociety.pojo.vo.SocietyVO;
import com.mouxianyu.studentsociety.pojo.vo.UserVO;
import com.mouxianyu.studentsociety.service.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Service
public class SocietyServiceImpl implements SocietyService {
    @Autowired
    private SocietyMapper societyMapper;

    @Autowired
    private RelUserSocietyService relUserSocietyService;

    @Autowired
    private UserService userService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private MajorService majorService;

    @Override
    public Society getById(Long id) {
        return societyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SocietyVO> queryByPage(SocietyDTO societyDTO) {
        Example example = condition(societyDTO);
        RowBounds rowBounds = new RowBounds(societyDTO.getStart(), societyDTO.getRow());
        List<SocietyVO> societyVOS = new ArrayList<>();
        List<Society> societies = societyMapper.selectByExampleAndRowBounds(example, rowBounds);
        for (Society society : societies) {
            SocietyVO societyVO = new SocietyVO();
            BeanUtils.copyProperties(society, societyVO);
            RelUserSociety relUserSociety = relUserSocietyService.getBySocietyIdAndRelation(society.getId(), UserSocietyRelationEnum.PRESIDENT.getCode());
            if (relUserSociety != null) {
                User user = userService.getById(relUserSociety.getUserId());
                societyVO.setPresidentName(user.getName());
                societyVO.setPresidentId(user.getId());
            }
            int userCount = relUserSocietyService.countBySocietyId(societyVO.getId());
            societyVO.setUserCount(userCount);
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
    public Long add(SocietyDTO societyDTO) {
        Society society = new Society();
        BeanUtils.copyProperties(societyDTO, society);
        society.setCreateTime(new Date());
        if (society.getStatus() == null) {
            society.setStatus(StatusEnum.INVALID.getCode());
        }
        if (society.getScale() == null) {
            society.setStatus(ScaleEnum.SCHOOL_LEVEL.getCode());
        }
        societyMapper.insertSelective(society);
        return society.getId();
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
            relUserSocietyService.deleteBySocietyId(id);
        }
    }

    @Override
    public void updateById(SocietyDTO societyDTO) {
        Society society = new Society();
        BeanUtils.copyProperties(societyDTO, society);
        society.setModifyTime(new Date());
        societyMapper.updateByPrimaryKeySelective(society);
    }

    @Override
    public Map<String, List<String>> countByCollege(Long societyId) {
        List<String> counts = new ArrayList<>();
        List<String> colleges = new ArrayList<>();
        Map<Long, Integer> relationMap = new HashMap<>();
        List<UserVO> userVOS = userService.queryBySocietyId(societyId);
        for (UserVO userVO : userVOS) {
            Long collegeId = userVO.getCollege();
            if (relationMap.containsKey(collegeId)) {
                Integer count = relationMap.get(collegeId) + 1;
                relationMap.put(collegeId, count);
            } else {
                relationMap.put(collegeId, 1);
            }
        }
        for (Long collegeId : relationMap.keySet()) {
            College college = collegeService.getById(collegeId);
            colleges.add(college.getName());
            counts.add(relationMap.get(collegeId).toString());
        }
        Map<String, List<String>> result = new HashMap<>(1);
        result.put("counts", counts);
        result.put("names", colleges);
        return result;
    }

    @Override
    public Map<String, List<String>> countByMajor(Long societyId) {
        List<String> counts = new ArrayList<>();
        List<String> majors = new ArrayList<>();
        Map<Long, Integer> relationMap = new HashMap<>();
        List<UserVO> userVOS = userService.queryBySocietyId(societyId);
        for (UserVO userVO : userVOS) {
            Long majorId = userVO.getMajorId();
            if (relationMap.containsKey(majorId)) {
                Integer count = relationMap.get(majorId) + 1;
                relationMap.put(majorId, count);
            } else {
                relationMap.put(majorId, 1);
            }
        }
        for (Long majorId : relationMap.keySet()) {
            Major major = majorService.getById(majorId);
            majors.add(major.getName());
            counts.add(relationMap.get(majorId).toString());
        }
        Map<String, List<String>> result = new HashMap<>(2);
        result.put("counts", counts);
        result.put("names", majors);
        return result;
    }

    @Override
    public Map<String, List<String>> countByGrade(Long societyId) {
        List<String> counts = new ArrayList<>();
        List<String> majors = new ArrayList<>();
        Map<String, Integer> relationMap = new HashMap<>();
        List<UserVO> userVOS = userService.queryBySocietyId(societyId);
        for (UserVO userVO : userVOS) {
            String grade = userVO.getGrade();
            if (relationMap.containsKey(grade)) {
                Integer count = relationMap.get(grade) + 1;
                relationMap.put(grade, count);
            } else {
                relationMap.put(grade, 1);
            }
        }
        for (String grade : relationMap.keySet()) {
            majors.add(grade);
            counts.add(relationMap.get(grade).toString());
        }
        Map<String, List<String>> result = new HashMap<>(2);
        result.put("counts", counts);
        result.put("names", majors);
        return result;
    }

    @Override
    public Map<String, List<String>> countByGender(Long societyId) {
        int maleCount = 0;
        int femaleCount = 0;
        List<String> genderList = new ArrayList<>();
        genderList.add("男");
        genderList.add("女");
        List<String> countList = new ArrayList<>();
        List<UserVO> userVOS = userService.queryBySocietyId(societyId);
        for (UserVO userVO : userVOS) {
            if (userVO.getGender()) {
                maleCount++;
            } else {
                femaleCount++;
            }
        }
        Map<String, List<String>> result = new HashMap<>(2);
        countList.add(Integer.toString(maleCount));
        countList.add(Integer.toString(femaleCount));
        result.put("counts", countList);
        result.put("names", genderList);
        return result;
    }

    private Example condition(SocietyDTO societyDTO) {
        Example example = new Example(Society.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("id").desc();
        if (!StringUtils.isEmpty(societyDTO.getName())) {
            criteria.andLike("name", "%" + societyDTO.getName() + "%");
        }
        if (societyDTO.getStatus() != null) {
            criteria.andEqualTo("status", societyDTO.getStatus());
        } else {
            criteria.andNotEqualTo("status", StatusEnum.DELETED.getCode());
        }

        return example;
    }
}
