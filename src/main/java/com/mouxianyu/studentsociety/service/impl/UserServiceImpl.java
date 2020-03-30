package com.mouxianyu.studentsociety.service.impl;

import com.mouxianyu.studentsociety.common.constant.Constant;
import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.mapper.UserMapper;
import com.mouxianyu.studentsociety.pojo.dto.UserDTO;
import com.mouxianyu.studentsociety.pojo.entity.Major;
import com.mouxianyu.studentsociety.pojo.entity.RelUserSociety;
import com.mouxianyu.studentsociety.pojo.entity.User;
import com.mouxianyu.studentsociety.pojo.vo.UserVO;
import com.mouxianyu.studentsociety.service.MajorService;
import com.mouxianyu.studentsociety.service.RelUserSocietyService;
import com.mouxianyu.studentsociety.service.UserService;
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
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MajorService majorService;

    @Autowired
    private RelUserSocietyService relUserSocietyService;

    @Override
    public User getById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getByUserNo(String userNo) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("no",userNo);
        return userMapper.selectOneByExample(example);
    }

    @Override
    public List<User> queryAll(UserDTO userDTO) {
        if(userDTO.getId()!=null){
            User user = getById(userDTO.getId());
            List<User> users = new ArrayList<>();
            users.add(user);
            return users;
        }
        Example example = condition(userDTO);
        RowBounds rowBounds = new RowBounds(userDTO.getStart(), userDTO.getRow());
        return userMapper.selectByExampleAndRowBounds(example, rowBounds);
    }

    @Override
    public List<UserVO> queryBySocietyId(Long societyId) {
        List<UserVO> userVOS = new ArrayList<>();
        List<RelUserSociety> relUserSocieties = relUserSocietyService.queryBySocietyId(societyId);
        for (RelUserSociety relUserSociety : relUserSocieties) {
            User user = getById(relUserSociety.getUserId());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user,userVO);
            userVO.setMajorId(user.getMajor());
            userVO.setRelation(relUserSociety.getRelation());
            userVO.setRelationStatus(relUserSociety.getStatus());
            Major major = new Major();
            if (user.getMajor() != null) {
                major = majorService.getById(user.getMajor());
            }
            if (major != null) {
                userVO.setMajor(major.getName());
            }
            userVOS.add(userVO);
        }
        return userVOS;
    }

    @Override
    public List<UserVO> queryByPage(UserDTO userDTO) {
        Example example = condition(userDTO);
        RowBounds rowBounds = new RowBounds(userDTO.getStart(), userDTO.getRow());
        List<UserVO> userVOS = new ArrayList<>();
        List<User> users = userMapper.selectByExampleAndRowBounds(example, rowBounds);
        for (User user : users) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVO.setMajorId(user.getMajor());
            Major major = new Major();
            if (user.getMajor() != null) {
                major = majorService.getById(user.getMajor());
            }
            if (major != null) {
                userVO.setMajor(major.getName());
            }
            userVOS.add(userVO);
        }
        return userVOS;
    }

    @Override
    public int getCountByCondition(UserDTO userDTO) {
        Example example = condition(userDTO);
        return userMapper.selectCountByExample(example);
    }

    @Override
    public void deleteById(Long id) {
        User user = new User();
        user.setId(id);
        user.setStatus(StatusEnum.DELETED.getCode());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void updateById(UserDTO userDTO) {
        User user = new User();
        if(userDTO.getMajor()!=null){
            Major major = majorService.getById(userDTO.getMajor());
            userDTO.setCollege(major.getCollegeId());
        }
        BeanUtils.copyProperties(userDTO,user);
        user.setModifyTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void add(UserDTO userDTO) {
        User user = new User();
        if(userDTO.getMajor()!=null){
            Major major = majorService.getById(userDTO.getMajor());
            userDTO.setCollege(major.getCollegeId());
        }
        BeanUtils.copyProperties(userDTO,user);
        user.setCreateTime(new Date());
        if(StringUtils.isEmpty(user.getPassword())){
            user.setPassword(Constant.DEFAULT_PASSWORD);
        }
        if(user.getStatus()==null){
            user.setStatus(StatusEnum.INVALID.getCode());
        }
        userMapper.insertSelective(user);
    }

    private Example condition(UserDTO userDTO) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("id").desc();
        if (!StringUtils.isEmpty(userDTO.getName())) {
            criteria.andLike("name", "%" + userDTO.getName() + "%");
        }
        if (!StringUtils.isEmpty(userDTO.getGrade())) {
            criteria.andLike("grade", "%" + userDTO.getGrade() + "%");
        }
        if (!StringUtils.isEmpty(userDTO.getNo())) {
            criteria.andLike("no", "%" + userDTO.getNo() + "%");
        }
        criteria.andNotEqualTo("status", StatusEnum.DELETED.getCode());
        return example;
    }
}
