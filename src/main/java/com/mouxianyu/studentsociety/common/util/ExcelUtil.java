package com.mouxianyu.studentsociety.common.util;

import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.pojo.entity.College;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
public class ExcelUtil {
    /**
     * 上传学院信息
     * @param inputStream
     * @param collegeName
     * @return java.util.List<com.mouxianyu.studentsociety.pojo.entity.College>
     */
    public static List<College> uploadCollege(InputStream inputStream, String collegeName) throws IOException {
        int collegeNameIndex = -1;
        List<College> collegeList = new ArrayList<>();
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
        }
        if(collegeNameIndex==-1){
            return null;
        }
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
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
                    College college = new College();
                    college.setName(cellValue);
                    college.setStatus(StatusEnum.NORMAL.getCode());
                    collegeList.add(college);
                }
            }
        }
        inputStream.close();
        return collegeList;
    }
}
