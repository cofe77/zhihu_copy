package com.meenie.controller;

import com.meenie.domain.Carousel;
import com.meenie.service.CarouselService;
import com.meenie.util.ImgUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/image")
public class UploadController {

    @Autowired
    private CarouselService carouselService;

    @RequestMapping(value = "/uploadImage",method = RequestMethod.POST)
    public ResResult addImage(@RequestParam("file")MultipartFile file){

        String suffixName = ImgUploadUtil.getImagePath(file);

        String newFileName = ImgUploadUtil.getNewFileName(suffixName);

        File nfile = new File(ImgUploadUtil.getNewImagePath(newFileName));

        boolean state = ImgUploadUtil.saveImage(file, nfile);

        int latestIndex = carouselService.getCarouselLatestIndex();

        Carousel carousel = new Carousel();
        carousel.setSrc("/static/media/carousel/" + newFileName);
        carousel.setImageIdx(latestIndex+1);

        if(state){
            carouselService.addCarousel(carousel);
        }

        return new ResResult(state? ResCode.ADD_OK:ResCode.ADD_ERR,state?"上传成功":"上传失败",state?"/static/media/carousel/" + newFileName:null);
    }
}
