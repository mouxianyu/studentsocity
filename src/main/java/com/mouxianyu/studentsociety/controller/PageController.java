package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.common.constant.Constant;
import com.mouxianyu.studentsociety.common.enums.AuthTypeEnum;
import com.mouxianyu.studentsociety.common.util.MD5Util;
import com.mouxianyu.studentsociety.common.util.MailUtils;
import com.mouxianyu.studentsociety.pojo.dto.*;
import com.mouxianyu.studentsociety.pojo.entity.User;
import com.mouxianyu.studentsociety.service.ActivityService;
import com.mouxianyu.studentsociety.service.CollegeService;
import com.mouxianyu.studentsociety.service.MajorService;
import com.mouxianyu.studentsociety.service.SocietyService;
import com.mouxianyu.studentsociety.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public String toHome(HttpServletRequest request) {
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
        request.setAttribute("userCount", userCount);
        request.setAttribute("adminCount", adminCount);
        request.setAttribute("societyCount", societyCount);
        request.setAttribute("collegeCount", collegeCount);
        request.setAttribute("majorCount", majorCount);
        request.setAttribute("activityCount", activityCount);
        return "home";
    }

    @RequestMapping("home/userChart")
    @ResponseBody
    private Map<String, List<String>> userChart() {
        return userService.countBystatus();
    }

    @RequestMapping("home/societyChart")
    @ResponseBody
    private Map<String, List<String>> societyChart() {
        return societyService.countBystatus();
    }

    @RequestMapping("home/activityChart")
    @ResponseBody
    private Map<String, List<String>> activityChart() {
        return activityService.countBystatus();
    }

    @RequestMapping("sendMail")
    @ResponseBody
    private String sendMail(String userNo, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = userService.getByNo(userNo);
        if (user == null) {
            return "用户不存在";
        }
        if (StringUtils.isEmpty(user.getEmail())) {
            return "用户邮箱为空";
        }
        String uuid = UUID.randomUUID().toString().substring(0, 6);
        boolean send = MailUtils.sendCheckCodeMail(user.getEmail(), uuid);
        if(send){
            request.getSession().setAttribute(Constant.MAIL_CHECK_CODE, MD5Util.encoderByMd5(uuid));
            return "";
        }
        else {
            return "发送失败";
        }
    }

    @RequestMapping("checkMailCode")
    @ResponseBody
    private String checkMailCode(String userNo,String checkCode, String password,HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String relCheckCode = (String)request.getSession().getAttribute(Constant.MAIL_CHECK_CODE);
        if(MD5Util.checkPassword(checkCode,relCheckCode)){
            User user = userService.getByNo(userNo);
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setPassword(password);
            userService.updateById(userDTO);
            return "";
        }else {
            return "验证码错误";
        }
    }

    @RequestMapping("forgetPassword")
    private String forgetPassword() {
        return "forget_password";
    }
}
