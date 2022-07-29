package com.meenie.controller;

import com.meenie.domain.Carousel;
import com.meenie.exception.BusinessException;
import com.meenie.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    @GetMapping
    public ResResult getCarousel(){
        return new ResResult(ResCode.GET_OK,"获取成功",carouselService.getCarousel());
    }

    @PostMapping
    public ResResult updateCarousel(@RequestBody Carousel carousel) {
        System.out.println(carousel.toString());
        if(null == carousel.getId()){
            System.out.println(carousel.getId());
            throw new BusinessException(ResCode.BUSINESS_ERR,"请输入id");
        }
        if(
            1 != carousel.getEnable() && 0 != carousel.getEnable() &&
            null == carousel.getHref() &&
            null == carousel.getImageIdx() &&
            null == carousel.getTitle()
        ){
            throw new BusinessException(ResCode.BUSINESS_ERR,"请输入正确的信息");
        }
        return new ResResult(ResCode.UPDATE_OK,"修改成功",carouselService.updateCarousel(carousel));
    }

    @DeleteMapping("/{id}")
    public ResResult deleteCarousel(@PathVariable Long id){
        return new ResResult(ResCode.DELETE_OK,"删除成功",carouselService.deleteCarouselById(id));
    };
}
