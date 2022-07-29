package com.meenie.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ImgUploadUtil {
    private final static String SAVE_IMAGE_PATH = "G:\\front-end\\zhihu-react-admin\\public\\static\\media\\carousel\\";
    // produ
    // private final static String SAVE_IMAGE_PATH = "G:\\nginx-1.22.0\\html\\static\\media\\carousel\\";

    public static String getImagePath(MultipartFile file){
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        int index = fileName.indexOf(".");
        return fileName.substring(index, fileName.length());
    }

    public static boolean saveImage(MultipartFile mfile, File file){
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try{
            mfile.transferTo(file);
            return true;
        }catch (IllegalStateException | IOException e){
            System.out.println(e);
            e.printStackTrace();
        }
        return false;
    }

    public static String getNewImagePath(String name){
        return SAVE_IMAGE_PATH + name;
    }

    public static String getNewFileName(String suffix){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        return date + UUID.randomUUID() + suffix;
    }
}
