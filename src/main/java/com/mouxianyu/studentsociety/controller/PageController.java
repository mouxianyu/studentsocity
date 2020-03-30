package com.mouxianyu.studentsociety.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Controller
public class PageController {

    @RequestMapping("home")
    public String toHome(){
        return "home";
    }
}
