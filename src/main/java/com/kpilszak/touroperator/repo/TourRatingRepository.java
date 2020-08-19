package com.kpilszak.touroperator.repo;

import com.kpilszak.touroperator.domain.TourRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends CrudRepository<TourRating, String> {
    List<TourRating> findAllByTourId(String tourId);
    Page<TourRating> findAllByTourId(String tourId, Pageable pageable);
    Optional<TourRating> findByTourIdAndCustomerId(String tourId, Integer customerId);
}
