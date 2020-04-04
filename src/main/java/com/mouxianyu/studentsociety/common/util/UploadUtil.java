package com.mouxianyu.studentsociety.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
public class UploadUtil {
    public static String upload(MultipartFile file,String filePath) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String newFileName = UUID.randomUUID()+originalFilename;
        File targetFile = new File(filePath,newFileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        file.transferTo(targetFile);
        return filePath+newFileName;
    }
}
