package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.common.constant.Constant;
import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.common.enums.UserSocietyRelationEnum;
import com.mouxianyu.studentsociety.pojo.dto.ActivityDTO;
import com.mouxianyu.studentsociety.pojo.dto.SocietyDTO;
import com.mouxianyu.studentsociety.pojo.entity.RelUserSociety;
import com.mouxianyu.studentsociety.pojo.entity.Society;
import com.mouxianyu.studentsociety.pojo.entity.User;
import com.mouxianyu.studentsociety.pojo.vo.ActivityVO;
import com.mouxianyu.studentsociety.pojo.vo.SocietyVO;
import com.mouxianyu.studentsociety.pojo.vo.UserVO;
import com.mouxianyu.studentsociety.service.ActivityService;
import com.mouxianyu.studentsociety.service.RelUserSocietyService;
import com.mouxianyu.studentsociety.service.SocietyService;
import com.mouxianyu.studentsociety.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Controller
@RequestMapping("client")
public class ClientController {

    @Autowired
    private SocietyService societyService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private RelUserSocietyService relUserSocietyService;

    @Autowired
    private UserService userService;

    @RequestMapping("home")
    public String client(HttpServletRequest request) {
        SocietyDTO societyDTO = new SocietyDTO();
        societyDTO.setStart(0);
        societyDTO.setRow(11);
        societyDTO.setStatus(StatusEnum.NORMAL.getCode());
        List<SocietyVO> societyVOS = societyService.queryByPageWithImg(societyDTO);

        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setStart(0);
        activityDTO.setRow(3);
        activityDTO.setStatus(StatusEnum.NORMAL.getCode());
        List<ActivityVO> topActivityVOList = activityService.queryByPageWithImg(activityDTO);

        List<ActivityVO> normalActivityVOList;
        if (topActivityVOList.size() < 3) {
            ActivityDTO normalCondition = new ActivityDTO();
            normalCondition.setStart(0);
            normalCondition.setRow(4);
            normalCondition.setStatus(StatusEnum.NORMAL.getCode());
            normalActivityVOList = activityService.queryByPageWithImg(normalCondition);
        } else {
            ActivityDTO normalCondition = new ActivityDTO();
            normalCondition.setStart(3);
            normalCondition.setRow(4);
            normalCondition.setStatus(StatusEnum.NORMAL.getCode());
            normalActivityVOList = activityService.queryByPageWithImg(normalCondition);
            if (normalActivityVOList.size() < 1) {
                normalCondition.setStart(0);
                normalActivityVOList = activityService.queryByPageWithImg(normalCondition);
            }
        }

        request.setAttribute("societies", societyVOS);
        request.setAttribute("topActivities", topActivityVOList);
        request.setAttribute("normalActivities", normalActivityVOList);
        return "client/home";
    }

    @RequestMapping("society/all")
    public String allSociety(HttpServletRequest request,String name) {
        SocietyDTO societyDTO = new SocietyDTO();
        if(!StringUtils.isEmpty(name)){
            societyDTO.setName(name);
        }
        societyDTO.setStart(0);
        societyDTO.setRow(5);
        societyDTO.setStatus(StatusEnum.NORMAL.getCode());
        List<SocietyVO> societyVOS = societyService.queryByPageWithImg(societyDTO);
        request.setAttribute("societies", societyVOS);
        request.setAttribute("societyName", name);
        return "client/society_list";
    }

    @RequestMapping("society/load")
    @ResponseBody
    public List<SocietyVO> loadSociety(int start,String name) {
        SocietyDTO societyDTO = new SocietyDTO();
        if(!StringUtils.isEmpty(name)){
            societyDTO.setName(name);
        }
        societyDTO.setStart(start);
        societyDTO.setRow(5);
        societyDTO.setStatus(StatusEnum.NORMAL.getCode());
        return societyService.queryByPageWithImg(societyDTO);
    }

    @RequestMapping("society/{id}")
    public String societyPage(@PathVariable Long id, HttpServletRequest request) {
        SocietyVO society = societyService.getByIdMore(id);
        User currentUser = (User) request.getSession().getAttribute(Constant.USER);
        if (currentUser != null) {
            RelUserSociety relation = relUserSocietyService.getByUserIdAndSocietyId(currentUser.getId(), society.getId());
            if (relation != null) {
                society.setRelationStatus(relation.getStatus());
            }
        }
        List<ActivityVO> activityVOS = activityService.queryBySocietyId(society.getId());
        request.setAttribute("activities", activityVOS);
        request.setAttribute("society", society);
        return "client/society_page";
    }

    @RequestMapping("society/manage/{id}")
    public String manageSociety(@PathVariable Long id, HttpServletRequest request) {
        SocietyVO society = societyService.getByIdMore(id);
        request.setAttribute("society", society);
        return "client/society_manage";
    }

    @RequestMapping("society/toModifyPage")
    public String toUpdateSociety(Long societyId, HttpServletRequest request) {
        Society society = societyService.getById(societyId);
        request.setAttribute("society", society);
        return "client/society_detail_modify";
    }

    @RequestMapping("society/update")
    @ResponseBody
    public void updateSociety(SocietyDTO societyDTO) {
        societyService.updateById(societyDTO);
    }

    @RequestMapping("society/allUser")
    public String allSocietyUser(SocietyDTO societyDTO, HttpServletRequest request) {
        societyDTO.setStatus(StatusEnum.NORMAL.getCode());
        List<UserVO> userVOS = userService.queryBySocietyIdAndCondition(societyDTO);
        request.setAttribute("users", userVOS);
        return "client/society_user_list";
    }

    @RequestMapping("society/apply")
    public String applyUser(SocietyDTO societyDTO, HttpServletRequest request) {
        societyDTO.setStatus(StatusEnum.AUDITING.getCode());
        List<UserVO> userVOS = userService.queryBySocietyIdAndCondition(societyDTO);
        request.setAttribute("users", userVOS);
        request.setAttribute("societyId", societyDTO.getId());
        return "client/society_apply_list";
    }

    @RequestMapping("society/consentApply")
    @ResponseBody
    public void consentApply(Long userId, Long societyId) {
        RelUserSociety oldRelation = relUserSocietyService.getByUserIdAndSocietyId(userId, societyId);
        RelUserSociety newRelation = new RelUserSociety();
        newRelation.setId(oldRelation.getId());
        newRelation.setStatus(StatusEnum.NORMAL.getCode());
        relUserSocietyService.updateById(newRelation);
    }

    @RequestMapping("society/activities")
    public String societyActivityList(Long societyId, HttpServletRequest request) {
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setSocietyId(societyId);
        activityDTO.setStart(0);
        activityDTO.setRow(10);
        List<ActivityVO> activityVOS = activityService.queryByPageWithImg(activityDTO);
        request.setAttribute("activities", activityVOS);
        request.setAttribute("societyId", societyId);
        return "client/society_activity";
    }

    @RequestMapping("society/activity/load")
    @ResponseBody
    public List<ActivityVO> activityLoad(Long societyId, int start) {
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setSocietyId(societyId);
        activityDTO.setStart(start);
        activityDTO.setRow(10);
        return activityService.queryByPageWithImg(activityDTO);
    }

    @RequestMapping("society/activity/toAdd")
    public String activityAdd(Long societyId, HttpServletRequest request) {
        request.setAttribute("societyId", societyId);
        return "client/society_activity_add";
    }

    @RequestMapping("society/activity/toDetail")
    public String activityDetail(Long activityId, HttpServletRequest request) {
        ActivityVO activityVO = activityService.getByIdMore(activityId);
        request.setAttribute("activity", activityVO);
        return "client/society_activity_detail";
    }
    @RequestMapping("society/applyFor")
    @ResponseBody
    public void applyForSociety(SocietyDTO societyDTO,HttpServletRequest request){
        User currentUser =(User) request.getSession().getAttribute(Constant.USER);
        societyDTO.setStatus(StatusEnum.AUDITING.getCode());
        societyDTO.setCreateId(currentUser.getId());
        Long societyId = societyService.add(societyDTO);
        RelUserSociety relUserSociety = new RelUserSociety();
        relUserSociety.setStatus(StatusEnum.NORMAL.getCode());
        relUserSociety.setRelation(UserSocietyRelationEnum.PRESIDENT.getCode());
        relUserSociety.setUserId(currentUser.getId());
        relUserSociety.setCreateId(currentUser.getId());
        relUserSociety.setSocietyId(societyId);
        relUserSocietyService.add(relUserSociety);
    }

    @RequestMapping("activity/{activityId}")
    public String activityPage(@PathVariable Long activityId, HttpServletRequest request) {
        ActivityVO activityVO = activityService.getByIdMore(activityId);
        request.setAttribute("activity", activityVO);
        return "client/activity_page";
    }

    @RequestMapping("activity/all")
    public String activityAll(HttpServletRequest request, ActivityDTO activityDTO) {
        activityDTO.setStart(0);
        activityDTO.setRow(6);
        activityDTO.setStatus(StatusEnum.NORMAL.getCode());
        List<ActivityVO> activityVOS = activityService.queryByPageWithImg(activityDTO);
        request.setAttribute("activities", activityVOS);
        request.setAttribute("activityTitle", activityDTO.getTitle());
        return "client/activity_all";
    }

    @RequestMapping("activity/load")
    @ResponseBody
    public List<ActivityVO> activityLoad(ActivityDTO activityDTO) {
        activityDTO.setRow(6);
        activityDTO.setStatus(StatusEnum.NORMAL.getCode());
        List<ActivityVO> activityVOS = activityService.queryByPageWithImg(activityDTO);
        return activityVOS;
    }

    @RequestMapping("zone")
    public String personalZone(){
        return "client/personal_zone";
    }

    @RequestMapping("zone/detail")
    public String zoneDetail(){
        return "client/personal_zone_detail";
    }

    @RequestMapping("zone/passwordPage")
    public String zonePasswordPage(){
        return "client/password";
    }

    @RequestMapping("zone/applySociety")
    public String applySociety(){
        return "client/apply_society";
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute(Constant.USER);
        return "client/login";
    }

    @RequestMapping("zone/mySociety/manage")
    public String mySocietyListManage(HttpServletRequest request){
        List<SocietyVO> societyVOS = new ArrayList<>();
        User user = (User)request.getSession().getAttribute(Constant.USER);
        List<RelUserSociety> relations = relUserSocietyService.queryByUserIdAndRelation(user.getId(), UserSocietyRelationEnum.PRESIDENT.getCode());
        for (RelUserSociety relation : relations) {
            SocietyVO societyVO = societyService.getByIdMore(relation.getSocietyId());
            if(societyVO!=null){
                societyVOS.add(societyVO);
            }
        }
        request.setAttribute("isManage",true);
        request.setAttribute("societies",societyVOS);
        return "client/my_society_list";
    }

    @RequestMapping("zone/mySociety/join")
    public String mySocietyListJoin(HttpServletRequest request){
        List<SocietyVO> societyVOS = new ArrayList<>();
        User user = (User)request.getSession().getAttribute(Constant.USER);
        List<RelUserSociety> relations = relUserSocietyService.queryByUserIdAndRelation(user.getId(), UserSocietyRelationEnum.MEMBER.getCode());
        for (RelUserSociety relation : relations) {
            SocietyVO societyVO = societyService.getByIdMore(relation.getSocietyId());
            societyVO.setRelationStatus(relation.getStatus());
            societyVOS.add(societyVO);
        }
        request.setAttribute("isManage",false);
        request.setAttribute("societies",societyVOS);
        return "client/my_society_list";
    }
}
