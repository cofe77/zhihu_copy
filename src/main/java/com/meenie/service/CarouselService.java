package com.meenie.service;

import com.meenie.domain.Carousel;

import java.util.List;

public interface CarouselService {
    void addCarousel(Carousel carousel);

    List<Carousel> getCarousel();

    int getCarouselLatestIndex();

    int updateCarousel(Carousel carousel);

    int deleteCarouselById(Long id);
}
