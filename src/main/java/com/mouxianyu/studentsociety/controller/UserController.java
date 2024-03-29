package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.common.constant.Constant;
import com.mouxianyu.studentsociety.common.enums.AuthTypeEnum;
import com.mouxianyu.studentsociety.common.enums.ObjTypeEnum;
import com.mouxianyu.studentsociety.common.util.MD5Util;
import com.mouxianyu.studentsociety.pojo.dto.UserDTO;
import com.mouxianyu.studentsociety.pojo.dto.UserImportDTO;
import com.mouxianyu.studentsociety.pojo.entity.College;
import com.mouxianyu.studentsociety.pojo.entity.Img;
import com.mouxianyu.studentsociety.pojo.entity.Major;
import com.mouxianyu.studentsociety.pojo.entity.User;
import com.mouxianyu.studentsociety.pojo.vo.UserVO;
import com.mouxianyu.studentsociety.service.CollegeService;
import com.mouxianyu.studentsociety.service.ImgService;
import com.mouxianyu.studentsociety.service.MajorService;
import com.mouxianyu.studentsociety.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @description: 用户控制层
 * @author: kingsme@yeah.net
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private ImgService imgService;


    @RequestMapping("{id}")
    @ResponseBody
    public User getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @RequestMapping("queryAll")
    @ResponseBody
    public List<User> queryAll(UserDTO userDTO) {
        userDTO.setStart(0);
        return userService.queryAll(userDTO);
    }

    @RequestMapping("toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("zone")
    public String zone() {
        return "my_zone";
    }

    @RequestMapping("toImport")
    public String toImport() {
        return "student_import";
    }

    @RequestMapping("upload")
    @ResponseBody
    public String upload(UserImportDTO userImportDTO, @RequestParam(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        return userService.upload(userImportDTO, multipartFile);
    }

    @RequestMapping("login")
    @ResponseBody
    public String login(String userNo, String userPassword, String checkCode, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = userService.getByNo(userNo);
        String relCheckCode = (String) request.getSession().getAttribute(Constant.IMAGE_CHECK_CODE);
        if (!checkCode.equalsIgnoreCase(relCheckCode)) {
            return "验证码错误";
        }
        if (user == null) {
            return "用户不存在";
        }
        if (!MD5Util.checkPassword(userPassword, user.getPassword())) {
            return "密码错误";
        }
        if (user.getAuthority() != AuthTypeEnum.ADMIN.getCode()) {
            return "您不是管理员";
        }
        updateSession(request, user);
        return "";
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constant.USER);
        return "login";
    }

    @RequestMapping("queryByPage")
    public String queryByPage(HttpServletRequest request, UserDTO userDTO) {
        UserDTO condition = new UserDTO();
        BeanUtils.copyProperties(userDTO, condition);
        userDTO.setStart((userDTO.getStart() - 1) * userDTO.getRow());
        List<UserVO> userVOS = userService.queryByPage(userDTO);
        int count = userService.getCountByCondition(userDTO);
        int totalPage = count % userDTO.getRow() == 0 ? count / userDTO.getRow() : count / userDTO.getRow() + 1;
        request.setAttribute("condition", condition);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("users", userVOS);
        List<Major> majors = majorService.queryAll();
        request.setAttribute("majors", majors);
        return "student_management";
    }

    @RequestMapping("delete")
    @ResponseBody
    public void delete(Long[] ids) {
        userService.deleteByIds(ids);
    }

    @RequestMapping("update")
    public String update(UserDTO userDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        userService.updateById(userDTO);
        return "redirect:/user/queryByPage";
    }

    @RequestMapping("updateIncludeSession")
    @ResponseBody
    public void updateIncludeSession(UserDTO userDTO, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = (User) request.getSession().getAttribute(Constant.USER);
        userDTO.setId(user.getId());
        userService.updateById(userDTO);
        User newUser = userService.getById(userDTO.getId());
        updateSession(request, newUser);
    }

    @RequestMapping("resetPassword")
    @ResponseBody
    public void resetPassword(Long id) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setPassword(Constant.DEFAULT_PASSWORD);
        userService.updateById(userDTO);
    }

    @RequestMapping("changePassword")
    @ResponseBody
    public String changePassword(HttpServletRequest request, String oldPassword, String newPassword) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User currentUser = (User) request.getSession().getAttribute(Constant.USER);
        User user = userService.getById(currentUser.getId());
        if (!MD5Util.checkPassword(oldPassword, user.getPassword())) {
            return "旧密码错误";
        } else {
            UserDTO userDTO = new UserDTO();
            userDTO.setPassword(newPassword);
            userDTO.setId(currentUser.getId());
            userService.updateById(userDTO);
            return "";
        }
    }

    @RequestMapping("add")
    public String add(UserDTO userDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        userService.add(userDTO);
        return "redirect:/user/queryByPage";
    }


    private void updateSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        Major major = majorService.getById(user.getMajor());
        College college = collegeService.getById(major.getCollegeId());
        List<Img> imgs = imgService.queryByTypeObjId(user.getId(), ObjTypeEnum.AVATAR.getCode());
        session.setAttribute(Constant.USER, user);
        session.setAttribute(Constant.MAJOR, major.getName());
        session.setAttribute(Constant.COLLEGE, college.getName());
        if (imgs != null && imgs.size() > 0) {
            session.setAttribute(Constant.AVATAR, imgs.get(0).getRelName());
        }
    }
}
