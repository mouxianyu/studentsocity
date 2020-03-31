package com.mouxianyu.studentsociety.service.impl;

import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.mapper.MajorMapper;
import com.mouxianyu.studentsociety.pojo.dto.MajorDTO;
import com.mouxianyu.studentsociety.pojo.entity.College;
import com.mouxianyu.studentsociety.pojo.entity.Major;
import com.mouxianyu.studentsociety.service.CollegeService;
import com.mouxianyu.studentsociety.service.MajorService;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    private MajorMapper majorMapper;

    @Autowired
    private CollegeService collegeService;

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

    @Override
    public List<Major> queryByPage(MajorDTO majorDTO) {
        Example condition = condition(majorDTO);
        RowBounds rowBounds = new RowBounds(majorDTO.getStart(), majorDTO.getRow());
        return majorMapper.selectByExampleAndRowBounds(condition,rowBounds);
    }

    @Override
    public int getCountByCondition(MajorDTO majorDTO) {
        Example condition = condition(majorDTO);
        return majorMapper.selectCountByExample(condition);
    }

    @Override
    public Long add(MajorDTO majorDTO) {
        Major major = new Major();
        BeanUtils.copyProperties(majorDTO,major);
        major.setStatus(StatusEnum.NORMAL.getCode());
        majorMapper.insertSelective(major);
        return major.getId();
    }

    @Override
    public void deleteById(Long id) {
        majorMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void updateById(MajorDTO majorDTO) {
        Major major = new Major();
        BeanUtils.copyProperties(majorDTO,major);
        majorMapper.updateByPrimaryKeySelective(major);
    }

    @Override
    public List<Major> queryByCollegeId(Long collegeId) {
        Example example = new Example(Major.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("collegeId",collegeId);
        return majorMapper.selectByExample(example);
    }

    @Override
    public String upload(MultipartFile multipartFile, String collegeName, String majorName) throws Exception {
        int collegeNameIndex = -1;
        int majorNameIndex =-1;
        InputStream inputStream = multipartFile.getInputStream();
        XSSFWorkbook book = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = book.getSheetAt(0);
        XSSFRow tableHead = sheet.getRow(0);
        for(int i=0;i<tableHead.getLastCellNum();i++){
            XSSFCell cell = tableHead.getCell(i);
            cell.setCellType(CellType.STRING);
            String cellValue = cell.getStringCellValue();
            if(cellValue.equals(collegeName)){
                collegeNameIndex=i;
            }
            if(cellValue.equals(majorName)){
                majorNameIndex=i;
            }
        }if(collegeNameIndex==-1||majorNameIndex==-1){
            return "表格中没有找到匹配的表头";
        }
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Major major = new Major();
            XSSFRow row = sheet.getRow(rowIndex);
            short lastCellNum = row.getLastCellNum();
            for (int cellIndex = 0; cellIndex < lastCellNum; cellIndex++) {
                XSSFCell cell = row.getCell(cellIndex);
                cell.setCellType(CellType.STRING);
                String cellValue = cell.getStringCellValue();
                if (StringUtils.isEmpty(cellValue)){
                    continue;
                }
                if (cellIndex == collegeNameIndex) {
                    List<College> collegeList = collegeService.queryByName(cellValue);
                    if(collegeList==null){
                        return "导入表格中有未存储学院，请先将所有学院导入到库中";
                    }
                    if(collegeList.size()>1){
                        return "数据库中学院信息【"+collegeList.get(0).getName()+"】有重复数据,请先删除";
                    }
                    major.setCollegeId(collegeList.get(0).getId());
                }
                if(cellIndex==majorNameIndex){
                    major.setName(cellValue);
                }
            }
            major.setStatus(StatusEnum.NORMAL.getCode());
            majorMapper.insertSelective(major);
        }
        return "";
    }

    @Override
    public List<Major> queryByName(String name) {
        Example example = new Example(Major.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name",name);
        criteria.andEqualTo("status",StatusEnum.NORMAL.getCode());
        return majorMapper.selectByExample(example);
    }

    private Example condition(MajorDTO majorDTO){
        Example example = new Example(Major.class);
        example.orderBy("id").desc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status",StatusEnum.NORMAL.getCode());
        if(majorDTO.getCollegeId()!=null){
            criteria.andEqualTo("collegeId",majorDTO.getCollegeId());
        }
        if(!StringUtils.isEmpty(majorDTO.getName())){
            criteria.andLike("name", "%" + majorDTO.getName() + "%");
        }
        return example;
    }
}
