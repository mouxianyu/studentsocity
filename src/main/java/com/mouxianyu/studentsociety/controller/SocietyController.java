package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.common.constant.Constant;
import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.common.enums.UserSocietyRelationEnum;
import com.mouxianyu.studentsociety.pojo.dto.SocietyDTO;
import com.mouxianyu.studentsociety.pojo.entity.RelUserSociety;
import com.mouxianyu.studentsociety.pojo.entity.Society;
import com.mouxianyu.studentsociety.pojo.entity.User;
import com.mouxianyu.studentsociety.pojo.vo.SocietyVO;
import com.mouxianyu.studentsociety.pojo.vo.UserVO;
import com.mouxianyu.studentsociety.service.RelUserSocietyService;
import com.mouxianyu.studentsociety.service.SocietyService;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Controller
@RequestMapping("society")
public class SocietyController {
    @Autowired
    private SocietyService societyService;

    @Autowired
    private RelUserSocietyService relUserSocietyService;

    @Autowired
    private UserService userService;

    @RequestMapping("{id}")
    @ResponseBody
    public SocietyVO getById(@PathVariable("id") Long id) {
        return societyService.getByIdMore(id);
    }

    @RequestMapping("toImport")
    public String toUpload() {
        return "society_import";
    }

    @RequestMapping("upload")
    @ResponseBody
    public String upload(String societyName, @RequestParam(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        return societyService.upload(societyName, multipartFile);
    }

    @RequestMapping("queryByPage")
    public String queryByPage(HttpServletRequest request, SocietyDTO societyDTO) {
        SocietyDTO condition = new SocietyDTO();
        BeanUtils.copyProperties(societyDTO, condition);
        societyDTO.setStart((societyDTO.getStart() - 1) * societyDTO.getRow());
        List<SocietyVO> societyVOS = societyService.queryByPage(societyDTO);
        int count = societyService.getCountByCondition(societyDTO);
        int totalPage = count % societyDTO.getRow() == 0 ? count / societyDTO.getRow() : count / societyDTO.getRow() + 1;
        request.setAttribute("condition", condition);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("societies", societyVOS);
        return "society_management";
    }

    @RequestMapping("queryUserBySocietyId/{id}")
    public String queryUserBySocietyId(HttpServletRequest request, @PathVariable("id") Long societyId) {
        SocietyDTO societyDTO = new SocietyDTO();
        societyDTO.setId(societyId);
        societyDTO.setStatus(StatusEnum.NORMAL.getCode());
        List<UserVO> userVOS = userService.queryBySocietyIdAndCondition(societyDTO);
        Society society = societyService.getById(societyId);
        request.setAttribute("users", userVOS);
        request.setAttribute("society", society);
        return "society_moreInfo";
    }

    @RequestMapping("chart/{id}")
    public String societyChart(HttpServletRequest request, @PathVariable("id") Long societyId) {
        Society society = societyService.getById(societyId);
        request.setAttribute("society", society);
        return "society_charts";
    }

    @RequestMapping("delete")
    @ResponseBody
    public void delete(Long[] ids) {
        societyService.deleteByIds(ids);
    }

    @RequestMapping("update")
    public String update(SocietyDTO societyDTO, Long presidentId) {
        societyService.updateById(societyDTO);
        RelUserSociety oldPresident = relUserSocietyService.getBySocietyIdAndRelation(societyDTO.getId(), UserSocietyRelationEnum.PRESIDENT.getCode());
        if (oldPresident != null) {
            oldPresident.setRelation(UserSocietyRelationEnum.MEMBER.getCode());
            oldPresident.setStatus(StatusEnum.NORMAL.getCode());
            relUserSocietyService.updateById(oldPresident);
        }
        RelUserSociety oldRelation = relUserSocietyService.getByUserIdAndSocietyId(presidentId, societyDTO.getId());
        if(oldRelation!=null){
            oldRelation.setRelation(UserSocietyRelationEnum.PRESIDENT.getCode());
            oldRelation.setStatus(StatusEnum.NORMAL.getCode());
            relUserSocietyService.updateById(oldRelation);
        }else {
            RelUserSociety newRel = new RelUserSociety();
            newRel.setUserId(presidentId);
            newRel.setRelation(UserSocietyRelationEnum.PRESIDENT.getCode());
            newRel.setSocietyId(societyDTO.getId());
            newRel.setStatus(StatusEnum.NORMAL.getCode());
            relUserSocietyService.add(newRel);
        }

        return "redirect:/society/queryByPage";
    }

    @RequestMapping("add")
    public String add(SocietyDTO societyDTO, Long presidentId) {
        Long societyId = societyService.add(societyDTO);
        RelUserSociety relUserSociety = new RelUserSociety();
        relUserSociety.setSocietyId(societyId);
        relUserSociety.setUserId(presidentId);
        relUserSociety.setStatus(StatusEnum.NORMAL.getCode());
        relUserSociety.setRelation(UserSocietyRelationEnum.PRESIDENT.getCode());
        relUserSocietyService.add(relUserSociety);
        return "redirect:/society/queryByPage";
    }

    @RequestMapping("join/{id}")
    @ResponseBody
    public String join(HttpServletRequest request, @PathVariable("id") Long societyId) {
        User user = (User) request.getSession().getAttribute(Constant.USER);
        RelUserSociety relUserSociety = relUserSocietyService.getByUserIdAndSocietyId(user.getId(), societyId);
        if (relUserSociety != null) {
            return "您已经加入或申请过该社团";
        }
        RelUserSociety newRelUserSociety = new RelUserSociety();
        newRelUserSociety.setSocietyId(societyId);
        newRelUserSociety.setUserId(user.getId());
        newRelUserSociety.setRelation(UserSocietyRelationEnum.MEMBER.getCode());
        relUserSocietyService.add(newRelUserSociety);
        return "";
    }

    @RequestMapping("mySociety")
    public String mySociety(HttpServletRequest request) {
        List<SocietyVO> societyVOS = new ArrayList<>();
        User user = (User) request.getSession().getAttribute(Constant.USER);
        List<RelUserSociety> relUserSocieties = relUserSocietyService.queryByUserId(user.getId());
        for (RelUserSociety relUserSociety : relUserSocieties) {
            SocietyVO societyVO = societyService.getByIdMore(relUserSociety.getSocietyId());
            societyVO.setRelationStatus(relUserSociety.getStatus());
            societyVOS.add(societyVO);
        }
        request.setAttribute("societies", societyVOS);
        return "client/my_society";
    }

    @RequestMapping("countByCollege/{id}")
    @ResponseBody
    public Map<String, List<String>> countByCollege(@PathVariable("id") Long societyId) {
        return societyService.countByCollege(societyId);
    }

    @RequestMapping("countByMajor/{id}")
    @ResponseBody
    public Map<String, List<String>> countByMajor(@PathVariable("id") Long societyId) {
        return societyService.countByMajor(societyId);
    }

    @RequestMapping("countByGrade/{id}")
    @ResponseBody
    public Map<String, List<String>> countByGrade(@PathVariable("id") Long societyId) {
        return societyService.countByGrade(societyId);
    }

    @RequestMapping("countByGender/{id}")
    @ResponseBody
    public Map<String, List<String>> countByGender(@PathVariable("id") Long societyId) {
        return societyService.countByGender(societyId);
    }
}
