package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.common.util.ExcelUtil;
import com.mouxianyu.studentsociety.pojo.entity.College;
import com.mouxianyu.studentsociety.service.CollegeService;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Controller
@RequestMapping("college")
public class CollegeController {
    @Autowired
    private CollegeService collegeService;

    @RequestMapping("queryAll")
    @ResponseBody
    public List<College> queryAll(){
        return collegeService.queryAll();
    }

    @RequestMapping("toImport")
    public String toImport(){
        return "college_import";
    }

    @RequestMapping("upload")
    @ResponseBody
    public String upLoad(@RequestParam( value="file",required=false) MultipartFile multipartFile,String collegeName) throws Exception {
        InputStream inputStream = multipartFile.getInputStream();
        List<College> collegeList = ExcelUtil.uploadCollege(inputStream, collegeName);
        if(collegeList==null){
            return "表格中没有找到相匹配的表头";
        }
        collegeService.addList(collegeList);
        return "";
    }


}
