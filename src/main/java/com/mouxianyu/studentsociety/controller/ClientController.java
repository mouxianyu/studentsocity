package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.common.constant.Constant;
import com.mouxianyu.studentsociety.common.enums.ObjTypeEnum;
import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.pojo.dto.ActivityDTO;
import com.mouxianyu.studentsociety.pojo.dto.SocietyDTO;
import com.mouxianyu.studentsociety.pojo.entity.Img;
import com.mouxianyu.studentsociety.pojo.entity.RelUserSociety;
import com.mouxianyu.studentsociety.pojo.entity.User;
import com.mouxianyu.studentsociety.pojo.vo.ActivityVO;
import com.mouxianyu.studentsociety.pojo.vo.SocietyVO;
import com.mouxianyu.studentsociety.service.ActivityService;
import com.mouxianyu.studentsociety.service.RelUserSocietyService;
import com.mouxianyu.studentsociety.service.SocietyService;
import com.mouxianyu.studentsociety.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
        User currentUser = (User)request.getSession().getAttribute(Constant.USER);
        if(currentUser!=null){
            RelUserSociety relation = relUserSocietyService.getByUserIdAndSocietyId(currentUser.getId(), society.getId());
            if(relation!=null){
                society.setRelationStatus(relation.getStatus());
            }
        }
        request.setAttribute("society",society);
        return "client/society_page";
    }

    @RequestMapping("society/manage/{id}")
    public String manageSociety(@PathVariable Long id,HttpServletRequest request){
        SocietyVO society = societyService.getByIdMore(id);
        request.setAttribute("society",society);
        return "client/society_manage";
    }

    @RequestMapping("toUploadImg/{type}")
    public String uploadImg(@PathVariable int type , HttpServletRequest request){
        if(ObjTypeEnum.SOCIETY.getCode()==type){

        }
        String url=null;
        request.setAttribute("url",url);
        return "client/img_upload";
    }
}
