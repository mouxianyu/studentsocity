package com.mouxianyu.studentsociety.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
        SocietyVO societyVO = new SocietyVO();
        Society society = societyService.getById(id);
        BeanUtils.copyProperties(society, societyVO);
        RelUserSociety president = relUserSocietyService.getBySocietyIdAndRelation(id, UserSocietyRelationEnum.PRESIDENT.getCode());
        RelUserSociety vicePresident = relUserSocietyService.getBySocietyIdAndRelation(id, UserSocietyRelationEnum.VICE_PRESIDENT.getCode());
        if (president != null) {
            societyVO.setPresidentId(president.getUserId());
            User user = userService.getById(president.getUserId());
            societyVO.setPresidentName(user.getName());
        }
        if (vicePresident != null) {
            societyVO.setVicePresidentId(vicePresident.getUserId());
            User user = userService.getById(vicePresident.getUserId());
            societyVO.setVicePresidentName(user.getName());
        }
        int userCount = relUserSocietyService.countBySocietyId(id);
        societyVO.setUserCount(userCount);
        return societyVO;
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
        return "society_list";
    }

    @RequestMapping("queryUserBySocietyId/{id}")
    public String queryUserBySocietyId(HttpServletRequest request, @PathVariable("id") Long societyId) {
        List<UserVO> userVOS = userService.queryBySocietyId(societyId);
        Society society = societyService.getById(societyId);
        request.setAttribute("users", userVOS);
        request.setAttribute("society", society);
        return "society_more_info";
    }

    @RequestMapping("chart/{id}")
    public String societyChart(HttpServletRequest request, @PathVariable("id") Long societyId) {
        Society society = societyService.getById(societyId);
        request.setAttribute("society", society);
        return "society_chart";
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
        oldPresident.setUserId(presidentId);
        relUserSocietyService.updateById(oldPresident);
        return "redirect:/society/queryByPage";
    }

    @RequestMapping("add")
    public String add(SocietyDTO societyDTO, Long presidentId) {
        Long societyId = societyService.add(societyDTO);
        RelUserSociety relUserSociety = new RelUserSociety();
        relUserSociety.setSocietyId(societyId);
        relUserSociety.setUserId(presidentId);
        relUserSociety.setRelation(UserSocietyRelationEnum.PRESIDENT.getCode());
        relUserSocietyService.add(relUserSociety);
        return "redirect:/society/queryByPage";
    }

    @RequestMapping("countByCollege/{id}")
    @ResponseBody
    public Map<String, List<String>> countByCollege(@PathVariable("id") Long societyId) {
        return societyService.countByCollege(societyId);
    }
}