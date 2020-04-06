package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.common.constant.Constant;
import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.pojo.dto.ActivityDTO;
import com.mouxianyu.studentsociety.pojo.entity.Activity;
import com.mouxianyu.studentsociety.pojo.entity.Society;
import com.mouxianyu.studentsociety.pojo.entity.User;
import com.mouxianyu.studentsociety.pojo.vo.ActivityVO;
import com.mouxianyu.studentsociety.service.ActivityService;
import com.mouxianyu.studentsociety.service.SocietyService;
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
import java.util.Date;
import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Controller
@RequestMapping("activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private SocietyService societyService;

    @RequestMapping("queryByPage")
    private String toActivity(HttpServletRequest request, ActivityDTO activityDTO) {
        ActivityDTO condition = new ActivityDTO();
        BeanUtils.copyProperties(activityDTO, condition);
        activityDTO.setStart((activityDTO.getStart() - 1) * activityDTO.getRow());
        List<ActivityVO> activities = activityService.queryByPage(activityDTO);
        int count = activityService.getCountByCondition(activityDTO);
        int totalPage = count % activityDTO.getRow() == 0 ? count / activityDTO.getRow() : count / activityDTO.getRow() + 1;
        List<Society> societies = societyService.queryAll();
        request.setAttribute("societies", societies);
        request.setAttribute("condition", condition);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("activities", activities);
        return "activity_management";
    }

    @RequestMapping("{id}")
    @ResponseBody
    public Activity getById(@PathVariable("id") Long id) {
        return activityService.getById(id);
    }

    @RequestMapping("more/{id}")
    @ResponseBody
    public ActivityVO getByIdMore(@PathVariable("id") Long id) {
        return activityService.getByIdMore(id);
    }

    @RequestMapping("add")
    @ResponseBody
    public void add(Activity activity, @RequestParam(value = "file", required = false) MultipartFile[] multipartFiles, HttpServletRequest request) throws IOException {
        User user = (User) request.getSession().getAttribute(Constant.USER);
        if (user != null) {
            activity.setCreateId(user.getId());
        }
        activityService.add(activity, multipartFiles);
    }

    @RequestMapping("update")
    @ResponseBody
    public void update(HttpServletRequest request, Activity activity, @RequestParam(value = "file", required = false) MultipartFile[] multipartFiles) throws IOException {
        User user = (User) request.getSession().getAttribute(Constant.USER);
        if (user != null) {
            activity.setModifyId(user.getId());
        }
        activityService.updateById(activity, multipartFiles);
    }

    @RequestMapping("delete")
    @ResponseBody
    public void delete(Long[] ids) {
        activityService.deleteByIds(ids);
    }

    @RequestMapping("pass")
    @ResponseBody
    public void pass(HttpServletRequest request, Long id) throws IOException {
        Activity activity = new Activity();
        activity.setEstTime(new Date());
        activity.setId(id);
        activity.setStatus(StatusEnum.NORMAL.getCode());
        User user = (User) request.getSession().getAttribute(Constant.USER);
        if (user != null) {
            activity.setModifyId(user.getId());
        }
        activityService.updateById(activity, null);
    }

    @RequestMapping("reject")
    @ResponseBody
    public void reject(HttpServletRequest request, Long id) throws IOException {
        Activity activity = new Activity();
        activity.setId(id);
        activity.setStatus(StatusEnum.REJECT.getCode());
        User user = (User) request.getSession().getAttribute(Constant.USER);
        if (user != null) {
            activity.setModifyId(user.getId());
        }
        activityService.updateById(activity, null);
    }

    @RequestMapping("cancel")
    @ResponseBody
    public void cancel(HttpServletRequest request, Long id) throws IOException {
        Activity activity = new Activity();
        activity.setId(id);
        activity.setStatus(StatusEnum.INVALID.getCode());
        User user = (User) request.getSession().getAttribute(Constant.USER);
        if (user != null) {
            activity.setModifyId(user.getId());
        }
        activityService.updateById(activity, null);
    }

    @RequestMapping("restart")
    @ResponseBody
    public void restart(HttpServletRequest request, Long id) throws IOException {
        Activity activity = new Activity();
        activity.setId(id);
        activity.setStatus(StatusEnum.NORMAL.getCode());
        User user = (User) request.getSession().getAttribute(Constant.USER);
        if (user != null) {
            activity.setModifyId(user.getId());
        }
        activityService.updateById(activity, null);
    }


    @RequestMapping("cancelReject")
    @ResponseBody
    public void cancelReject(HttpServletRequest request, Long id) throws IOException {
        Activity activity = new Activity();
        activity.setId(id);
        activity.setStatus(StatusEnum.AUDITING.getCode());
        User user = (User) request.getSession().getAttribute(Constant.USER);
        if (user != null) {
            activity.setModifyId(user.getId());
        }
        activityService.updateById(activity, null);
    }


}
