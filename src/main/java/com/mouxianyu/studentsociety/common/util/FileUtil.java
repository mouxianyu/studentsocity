package com.mouxianyu.studentsociety.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
public class FileUtil {
    public static String upload(MultipartFile file,String filePath) throws IOException {
        String newFileName =UUID.randomUUID()+file.getOriginalFilename();
        File targetFile = new File(filePath,newFileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        file.transferTo(targetFile);
        return newFileName;
    }

    public static boolean delete(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            return file.delete();
        }
        return false;
    }
}
