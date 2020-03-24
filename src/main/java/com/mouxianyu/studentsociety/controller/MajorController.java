package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.pojo.entity.Major;
import com.mouxianyu.studentsociety.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
