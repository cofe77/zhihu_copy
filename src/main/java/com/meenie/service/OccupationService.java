package com.meenie.service;

import com.meenie.domain.Occupation;

public interface OccupationService {

    boolean add(Occupation occupation);

    boolean delete(Long id);
}
