package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.pojo.dto.MajorDTO;
import com.mouxianyu.studentsociety.pojo.entity.Major;
import com.mouxianyu.studentsociety.service.MajorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Controller
@RequestMapping("major")
public class MajorController {
    @Autowired
    private MajorService majorService;

    @RequestMapping("/queryByCollegeId/{id}")
    @ResponseBody
    public List<Major> queryByCollegeId(@PathVariable("id") Long id){
        return majorService.queryByCollegeId(id);
    }

    @RequestMapping("toImport")
    public String toImport(){
        return "major_import";
    }


    @RequestMapping("upload")
    @ResponseBody
    public String upload(@RequestParam( value="file",required=false) MultipartFile multipartFile, String collegeName,String majorName) throws IOException {
        return majorService.upload(multipartFile,collegeName,majorName);
    }

    @RequestMapping("queryByPage")
    public String queryByPage(HttpServletRequest request, MajorDTO majorDTO){
        MajorDTO condition = new MajorDTO();
        BeanUtils.copyProperties(majorDTO,condition);
        majorDTO.setStart((majorDTO.getStart() - 1) * majorDTO.getRow());
        List<Major> majorList = majorService.queryByPage(majorDTO);
        int count = majorService.getCountByCondition(majorDTO);
        int totalPage = count % majorDTO.getRow() == 0 ? count / majorDTO.getRow() : count / majorDTO.getRow() + 1;
        request.setAttribute("condition", condition);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("majors", majorList);
        return "major_management";
    }

    @RequestMapping("{id}")
    @ResponseBody
    public Major getById(@PathVariable("id") Long id){
        return majorService.getById(id);
    }

    @RequestMapping("update")
    public String update(MajorDTO majorDTO){
        majorService.updateById(majorDTO);
        return "redirect:/major/queryByPage";
    }

    @RequestMapping("add")
    public String add(MajorDTO majorDTO){
        majorService.add(majorDTO);
        return "redirect:/major/queryByPage";
    }

    @RequestMapping("delete")
    @ResponseBody
    public void deleteByIds(Long[] ids){
        majorService.deleteByIds(ids);
    }
}
