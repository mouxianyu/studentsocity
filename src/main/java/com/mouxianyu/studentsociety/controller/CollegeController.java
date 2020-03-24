package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.pojo.entity.College;
import com.mouxianyu.studentsociety.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
