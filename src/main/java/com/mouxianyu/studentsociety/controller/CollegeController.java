package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.common.util.ExcelUtil;
import com.mouxianyu.studentsociety.pojo.dto.CollegeDTO;
import com.mouxianyu.studentsociety.pojo.entity.College;
import com.mouxianyu.studentsociety.service.CollegeService;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String upLoad(@RequestParam( value="file",required=false) MultipartFile multipartFile,String collegeName) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        List<College> collegeList = ExcelUtil.uploadCollege(inputStream, collegeName);
        if(collegeList==null){
            return "表格中没有找到相匹配的表头";
        }
        collegeService.addList(collegeList);
        return "";
    }

    @RequestMapping("queryByPage")
    public String queryByPage(HttpServletRequest request, CollegeDTO collegeDTO){
        CollegeDTO condition = new CollegeDTO();
        BeanUtils.copyProperties(collegeDTO,condition);
        collegeDTO.setStart((collegeDTO.getStart() - 1) * collegeDTO.getRow());
        List<College> collegeList = collegeService.queryByPage(collegeDTO);
        int count = collegeService.getCountByCondition(collegeDTO);
        int totalPage = count % collegeDTO.getRow() == 0 ? count / collegeDTO.getRow() : count / collegeDTO.getRow() + 1;
        request.setAttribute("condition", condition);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("colleges", collegeList);
        return "college_management";
    }

    @RequestMapping("{id}")
    @ResponseBody
    public College getById(@PathVariable("id") Long id){
        return collegeService.getById(id);
    }

    @RequestMapping("update")
    public String update(CollegeDTO collegeDTO){
        collegeService.updateById(collegeDTO);
        return "redirect:/college/queryByPage";
    }

    @RequestMapping("add")
    public String add(CollegeDTO collegeDTO){
        collegeService.add(collegeDTO);
        return "redirect:/college/queryByPage";
    }

    @RequestMapping("delete")
    @ResponseBody
    public void deleteByIds(Long[] ids){
        collegeService.deleteByIds(ids);
    }


}
