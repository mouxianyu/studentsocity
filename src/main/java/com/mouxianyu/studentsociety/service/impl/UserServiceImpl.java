package com.mouxianyu.studentsociety.service.impl;

import com.mouxianyu.studentsociety.common.constant.Constant;
import com.mouxianyu.studentsociety.common.enums.AuthTypeEnum;
import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.mapper.UserMapper;
import com.mouxianyu.studentsociety.pojo.dto.UserDTO;
import com.mouxianyu.studentsociety.pojo.dto.UserImportDTO;
import com.mouxianyu.studentsociety.pojo.entity.College;
import com.mouxianyu.studentsociety.pojo.entity.Major;
import com.mouxianyu.studentsociety.pojo.entity.RelUserSociety;
import com.mouxianyu.studentsociety.pojo.entity.User;
import com.mouxianyu.studentsociety.pojo.vo.UserVO;
import com.mouxianyu.studentsociety.service.CollegeService;
import com.mouxianyu.studentsociety.service.MajorService;
import com.mouxianyu.studentsociety.service.RelUserSocietyService;
import com.mouxianyu.studentsociety.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.InputStream;
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
    private CollegeService collegeService;

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
        criteria.andEqualTo("no", userNo);
        return userMapper.selectOneByExample(example);
    }

    @Override
    public List<User> queryAll(UserDTO userDTO) {
        if (userDTO.getId() != null) {
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
            BeanUtils.copyProperties(user, userVO);
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
        if (userDTO.getMajor() != null) {
            Major major = majorService.getById(userDTO.getMajor());
            userDTO.setCollege(major.getCollegeId());
        }
        BeanUtils.copyProperties(userDTO, user);
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
        if (userDTO.getMajor() != null) {
            Major major = majorService.getById(userDTO.getMajor());
            userDTO.setCollege(major.getCollegeId());
        }
        BeanUtils.copyProperties(userDTO, user);
        user.setCreateTime(new Date());
        if (StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(Constant.DEFAULT_PASSWORD);
        }
        if (user.getStatus() == null) {
            user.setStatus(StatusEnum.INVALID.getCode());
        }
        userMapper.insertSelective(user);
    }

    @Override
    public String upload(UserImportDTO userImportDTO, MultipartFile multipartFile) throws Exception {
        int stuNoIndex = -1;
        int stuNameIndex = -1;
        int stuMajorIndex = -1;
        int stuGradeIndex = -1;
        int stuGenderIndex = -1;
        int stuOriginIndex = -1;
        int stuEmailIndex = -1;
        int stuPhoneIndex = -1;
        InputStream inputStream = multipartFile.getInputStream();
        XSSFWorkbook book = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = book.getSheetAt(0);
        XSSFRow tableHead = sheet.getRow(0);
        for (int i = 0; i < tableHead.getLastCellNum(); i++) {
            XSSFCell cell = tableHead.getCell(i);
            cell.setCellType(CellType.STRING);
            String cellValue = cell.getStringCellValue();
            if (cellValue.equals(userImportDTO.getNo())) {
                stuNoIndex = i;
            }
            if (cellValue.equals(userImportDTO.getName())) {
                stuNameIndex = i;
            }
            if (cellValue.equals(userImportDTO.getGrade())) {
                stuGradeIndex = i;
            }
            if (cellValue.equals(userImportDTO.getGender())) {
                stuGenderIndex = i;
            }
            if (cellValue.equals(userImportDTO.getMajor())) {
                stuMajorIndex = i;
            }
            if (cellValue.equals(userImportDTO.getOrigin())) {
                stuOriginIndex = i;
            }
            if (cellValue.equals(userImportDTO.getPhone())) {
                stuPhoneIndex = i;
            }
            if (cellValue.equals(userImportDTO.getEmail())) {
                stuEmailIndex = i;
            }
        }
        if (stuNoIndex == -1 || stuNameIndex == -1 || stuGenderIndex == -1 || stuGradeIndex == -1 || stuMajorIndex == -1) {
            return "表格中没有找到匹配的表头";
        }
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            User user = new User();
            XSSFRow row = sheet.getRow(rowIndex);
            short lastCellNum = row.getLastCellNum();
            for (int cellIndex = 0; cellIndex < lastCellNum; cellIndex++) {
                XSSFCell cell = row.getCell(cellIndex);
                cell.setCellType(CellType.STRING);
                String cellValue = cell.getStringCellValue();
                if (StringUtils.isEmpty(cellValue)){
                    continue;
                }
                if (cellIndex == stuMajorIndex) {
                    List<Major> majorList = majorService.queryByName(cellValue);
                    if (majorList == null) {
                        return "导入表格中有未存储专业，请先将所有专业导入到库中";
                    }
                    if (majorList.size() > 1) {
                        return "数据库中专业信息【" + majorList.get(0).getName() + "】有重复数据,请先删除";
                    }
                    user.setMajor(majorList.get(0).getId());
                    user.setCollege(majorList.get(0).getCollegeId());
                }
                if (cellIndex == stuNameIndex) {
                    user.setName(cellValue);
                }
                if (cellIndex == stuNoIndex) {
                    user.setNo(cellValue);
                }
                if (cellIndex == stuGradeIndex) {
                    user.setGrade(cellValue);
                }
                if (cellIndex == stuGenderIndex) {
                    if ("男".equals(cellValue)) {
                        user.setGender(true);
                    }
                    if ("女".equals(cellValue)) {
                        user.setGender(false);
                    }
                }
                if (cellIndex == stuOriginIndex) {
                    user.setOrigin(cellValue);
                }
                if (cellIndex == stuEmailIndex) {
                    user.setEmail(cellValue);
                }
                if (cellIndex == stuPhoneIndex) {
                    user.setPhone(cellValue);
                }
            }
            user.setPassword(user.getNo());
            user.setStatus(StatusEnum.NORMAL.getCode());
            user.setAuthority(AuthTypeEnum.USER.getCode());
            user.setCreateTime(new Date());
            userMapper.insertSelective(user);
        }
        return "";
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
