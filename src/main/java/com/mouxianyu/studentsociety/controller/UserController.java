package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.common.constant.Constant;
import com.mouxianyu.studentsociety.pojo.dto.UserDTO;
import com.mouxianyu.studentsociety.pojo.entity.Major;
import com.mouxianyu.studentsociety.pojo.entity.User;
import com.mouxianyu.studentsociety.pojo.vo.UserVO;
import com.mouxianyu.studentsociety.service.MajorService;
import com.mouxianyu.studentsociety.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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


    @RequestMapping("{id}")
    @ResponseBody
    public User getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @RequestMapping("queryAll")
    @ResponseBody
    public List<User> queryAll(){
        return userService.queryAll();
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
        request.setAttribute("majors",majors);
        return "student_list";
    }

    @RequestMapping("delete")
    @ResponseBody
    public void delete(Long [] ids){
        userService.deleteByIds(ids);
    }

    @RequestMapping("update")
    public String update(UserDTO userDTO){
        userService.updateById(userDTO);
        return "redirect:/user/queryByPage";
    }

    @RequestMapping("resetPassword")
    @ResponseBody
    public void resetPassword(Long id){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setPassword(Constant.DEFAULT_PASSWORD);
        userService.updateById(userDTO);
    }

    @RequestMapping("add")
    public String add(UserDTO userDTO){
        userService.add(userDTO);
        return "redirect:/user/queryByPage";
    }
}
