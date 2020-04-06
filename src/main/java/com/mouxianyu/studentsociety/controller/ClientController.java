package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.pojo.dto.ActivityDTO;
import com.mouxianyu.studentsociety.pojo.dto.SocietyDTO;
import com.mouxianyu.studentsociety.pojo.vo.ActivityVO;
import com.mouxianyu.studentsociety.pojo.vo.SocietyVO;
import com.mouxianyu.studentsociety.service.ActivityService;
import com.mouxianyu.studentsociety.service.SocietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping("home")
    public String client(HttpServletRequest request){
        SocietyDTO societyDTO = new SocietyDTO();
        societyDTO.setStart(0);
        societyDTO.setRow(11);
        societyDTO.setStatus(StatusEnum.NORMAL.getCode());
        List<SocietyVO> societyVOS = societyService.queryByPageWithImg(societyDTO);

        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setStart(0);
        activityDTO.setRow(4);
        activityDTO.setStatus(StatusEnum.NORMAL.getCode());
        List<ActivityVO> activityVOS = activityService.queryByPageWithImg(activityDTO);

        request.setAttribute("societies",societyVOS);
        request.setAttribute("activities",activityVOS);
        return "client/home";
    }

    @RequestMapping("society/all")
    public String allSociety(HttpServletRequest request){
        SocietyDTO societyDTO = new SocietyDTO();
        societyDTO.setStart(0);
        societyDTO.setRow(5);
        societyDTO.setStatus(StatusEnum.NORMAL.getCode());
        List<SocietyVO> societyVOS = societyService.queryByPageWithImg(societyDTO);
        request.setAttribute("societies",societyVOS);
        return "client/society_list";
    }

    @RequestMapping("society/load")
    @ResponseBody
    public List<SocietyVO> loadSociety(int start){
        SocietyDTO societyDTO = new SocietyDTO();
        societyDTO.setStart(start);
        societyDTO.setRow(8);
        societyDTO.setStatus(StatusEnum.NORMAL.getCode());
        return societyService.queryByPageWithImg(societyDTO);
    }

    @RequestMapping("society/{id}")
    public String societyPage(@PathVariable Long id,HttpServletRequest request){
        SocietyVO society = societyService.getByIdMore(id);
        request.setAttribute("society",society);
        return "client/society_page";
    }
}
