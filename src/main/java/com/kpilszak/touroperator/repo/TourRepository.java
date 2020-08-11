package com.kpilszak.touroperator.repo;

import com.kpilszak.touroperator.domain.Tour;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<Tour, Integer> {

}
