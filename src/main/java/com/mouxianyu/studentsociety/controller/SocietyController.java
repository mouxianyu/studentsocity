package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.pojo.dto.SocietyDTO;
import com.mouxianyu.studentsociety.pojo.entity.Society;
import com.mouxianyu.studentsociety.pojo.vo.SocietyVO;
import com.mouxianyu.studentsociety.service.SocietyService;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("society")
public class SocietyController {
    @Autowired
    private SocietyService societyService;

    @RequestMapping("{id}")
    @ResponseBody
    public Society getById(@PathVariable("id") Long id) {
        return societyService.getById(id);
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

    @RequestMapping("delete")
    @ResponseBody
    public void delete(Long [] ids){
        societyService.deleteByIds(ids);
    }

    @RequestMapping("update")
    public String update(SocietyDTO societyDTO){
        societyService.updateById(societyDTO);
        return "redirect:/society/queryByPage";
    }

    @RequestMapping("add")
    public String add(SocietyDTO societyDTO){
        societyService.add(societyDTO);
        return "redirect:/society/queryByPage";
    }
}
