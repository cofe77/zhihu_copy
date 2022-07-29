package com.meenie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meenie.dao.CarouselDao;
import com.meenie.domain.Carousel;
import com.meenie.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselDao carouselDao;

    @Override
    public void addCarousel(Carousel carousel) {
        carouselDao.insert(carousel);
    }

    @Override
    public List<Carousel> getCarousel() {
        return carouselDao.selectList(null);
    }

    @Override
    public int getCarouselLatestIndex() {
        return carouselDao.selectOne(new QueryWrapper<Carousel>().select("max(image_idx) as imageIdx")).getImageIdx();
    }

    @Override
    public int updateCarousel(Carousel carousel) {
        if(null != carousel.getImageIdx()){
            int diff = carousel.getImageIdx();//若diff=-1
            Carousel carousel1 = carouselDao.selectById(carousel.getId());// 当前修改项 imageIndex = 3
            Carousel carousel2 = carouselDao.selectOne(
                    new LambdaQueryWrapper<Carousel>()
                            .eq(Carousel::getImageIdx, carousel1.getImageIdx() + diff));// 被影响项 imageIndex = 3 - 1
            carousel.setImageIdx(carousel1.getImageIdx()+diff);
            carousel2.setImageIdx(carousel1.getImageIdx());
            carouselDao.updateById(carousel2);
            return carouselDao.updateById(carousel);
        }
        return carouselDao.updateById(carousel);
    }

    @Override
    public int deleteCarouselById(Long id) {
        return carouselDao.deleteById(id);
    }
}
