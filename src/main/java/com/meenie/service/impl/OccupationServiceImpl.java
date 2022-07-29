package com.meenie.service.impl;

import com.meenie.dao.OccupationDao;
import com.meenie.domain.Occupation;
import com.meenie.service.OccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OccupationServiceImpl implements OccupationService {

    @Autowired
    private OccupationDao occupationDao;

    @Override
    public boolean add(Occupation occupation) {
        occupationDao.insert(occupation);
        return false;
    }

    @Override
    public boolean delete(Long id) {
        occupationDao.deleteById(id);
        return false;
    }
}
