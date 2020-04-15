package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.common.enums.AuthTypeEnum;
import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.pojo.dto.*;
import com.mouxianyu.studentsociety.service.ActivityService;
import com.mouxianyu.studentsociety.service.CollegeService;
import com.mouxianyu.studentsociety.service.MajorService;
import com.mouxianyu.studentsociety.service.SocietyService;
import com.mouxianyu.studentsociety.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @Autowired
    private SocietyService societyService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private MajorService majorService;

    @RequestMapping("home")
    public String toHome(HttpServletRequest request){
        UserDTO userDTO = new UserDTO();
        userDTO.setAuthority(AuthTypeEnum.USER.getCode());
        int userCount = userService.getCountByCondition(userDTO);
        UserDTO adminDTO = new UserDTO();
        adminDTO.setAuthority(AuthTypeEnum.ADMIN.getCode());
        int adminCount = userService.getCountByCondition(adminDTO);
        SocietyDTO societyDTO = new SocietyDTO();
        int societyCount = societyService.getCountByCondition(societyDTO);
        ActivityDTO activityDTO = new ActivityDTO();
        int activityCount = activityService.getCountByCondition(activityDTO);
        CollegeDTO collegeDTO = new CollegeDTO();
        int collegeCount = collegeService.getCountByCondition(collegeDTO);
        MajorDTO majorDTO = new MajorDTO();
        int majorCount = majorService.getCountByCondition(majorDTO);
        request.setAttribute("userCount",userCount);
        request.setAttribute("adminCount",adminCount);
        request.setAttribute("societyCount",societyCount);
        request.setAttribute("collegeCount",collegeCount);
        request.setAttribute("majorCount",majorCount);
        request.setAttribute("activityCount",activityCount);
        return "home";
    }

    @RequestMapping("home/userChart")
    @ResponseBody
    private Map<String, List<String>> userChart(){
        return userService.countBystatus();
    }

    @RequestMapping("home/societyChart")
    @ResponseBody
    private Map<String, List<String>> societyChart(){
        return societyService.countBystatus();
    }

    @RequestMapping("home/activityChart")
    @ResponseBody
    private Map<String, List<String>> activityChart(){
        return activityService.countBystatus();
    }
}
