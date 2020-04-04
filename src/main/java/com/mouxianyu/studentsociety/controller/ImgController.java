package com.mouxianyu.studentsociety.controller;

import com.mouxianyu.studentsociety.common.config.ImgConfig;
import com.mouxianyu.studentsociety.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Controller
@RequestMapping("img")
public class ImgController {

    @Autowired
    private ImgConfig imgConfig;

    @Autowired
    private ImgService imgService;


    @RequestMapping(value = "activity/{imgName}", method = RequestMethod.GET)
    public void readActivityImg(@PathVariable String imgName, HttpServletResponse response) throws IOException {
        readImg(imgConfig.getActivity(),imgName,response);
    }

    @RequestMapping(value = "society/{imgName}", method = RequestMethod.GET)
    public void readSocietyImg(@PathVariable String imgName, HttpServletResponse response) throws IOException {
        readImg(imgConfig.getActivity(),imgName,response);
    }

    @RequestMapping(value = "avatar/{imgName}", method = RequestMethod.GET)
    public void readAvatarImg(@PathVariable String imgName, HttpServletResponse response) throws IOException {
        readImg(imgConfig.getActivity(),imgName,response);
    }

    @RequestMapping(value = "news/{imgName}", method = RequestMethod.GET)
    public void readNewsImg(@PathVariable String imgName, HttpServletResponse response) throws IOException {
        readImg(imgConfig.getActivity(),imgName,response);
    }

    @RequestMapping("delete/{id}")
    @ResponseBody
    public void deleteById(@PathVariable Long id){
        imgService.deleteById(id);
    }

    @RequestMapping("deleteByTypeAndObjId")
    @ResponseBody
    public void deleteByTypeAndObjId(Long objId,Integer type){
        imgService.deleteByTypeAndObjId(objId,type);
    }


    /**
     * io读取图片
     * @param path
     * @param imgName
     * @param response
     * @throws IOException
     */
    private void readImg(String path,String imgName,HttpServletResponse response) throws IOException {
        ServletOutputStream out = null;
        FileInputStream ips = null;
        try {
            //获取图片存放路径
            String imgPath = path + imgName;
            ips = new FileInputStream(new File(imgPath));
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
            ips.close();
        }
    }
}
