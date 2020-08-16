package com.kpilszak.touroperator.repo;

import com.kpilszak.touroperator.domain.TourRating;
import com.kpilszak.touroperator.domain.TourRatingPk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends CrudRepository<TourRating, TourRatingPk> {
    List<TourRating> findAllByPkTourId(Integer tourId);
    Page<TourRating> findAllByPkTourId(Integer tourId, Pageable pageable);
    TourRating findByPkTourIdAndPkCustomerId(Integer tourId, Integer customerId);
}
