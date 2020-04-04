package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.common.config.ImgConfig;
import com.mouxianyu.studentsociety.common.util.UploadUtil;
import com.mouxianyu.studentsociety.pojo.dto.ActivityDTO;
import com.mouxianyu.studentsociety.pojo.entity.Activity;
import com.mouxianyu.studentsociety.pojo.vo.ActivityVO;
import com.mouxianyu.studentsociety.service.ActivityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    private ImgConfig imgConfig;

    @RequestMapping("queryByPage")
    private String toActivity(HttpServletRequest request, ActivityDTO activityDTO){
        ActivityDTO condition = new ActivityDTO();
        BeanUtils.copyProperties(activityDTO, condition);
        activityDTO.setStart((activityDTO.getStart() - 1) * activityDTO.getRow());
        List<ActivityVO> activities = activityService.queryByPage(activityDTO);
        int count = activityService.getCountByCondition(activityDTO);
        int totalPage = count % activityDTO.getRow() == 0 ? count / activityDTO.getRow() : count / activityDTO.getRow() + 1;
        request.setAttribute("condition", condition);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("activities", activities);
        return "activity_management";
    }

    @RequestMapping("{id}")
    @ResponseBody
    public Activity getById(@PathVariable("id") Long id){
        return activityService.getById(id);
    }

    @RequestMapping("more/{id}")
    @ResponseBody
    public Activity getByIdMore(@PathVariable("id") Long id){
        return activityService.getById(id);
    }

    @RequestMapping("add")
    @ResponseBody
    public void add(Activity activity,@RequestParam( value="file",required=false) MultipartFile[] multipartFiles) throws Exception {
        for (MultipartFile multipartFile : multipartFiles) {
            String filename = multipartFile.getOriginalFilename();
            String filePath = UploadUtil.upload(multipartFile, imgConfig.getActivity());
            System.out.println(filePath);
            System.out.println(filename);
        }
        activityService.add(activity);
    }

    @RequestMapping("update")
    public void update(Activity activity){
        activityService.updateById(activity);
    }

    @RequestMapping("delete")
    @ResponseBody
    public void delete(Long[] ids){
        activityService.deleteByIds(ids);
    }
}
