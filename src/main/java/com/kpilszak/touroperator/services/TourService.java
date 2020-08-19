package com.kpilszak.touroperator.services;

import com.kpilszak.touroperator.domain.Tour;
import com.kpilszak.touroperator.domain.TourPackage;
import com.kpilszak.touroperator.repo.TourPackageRepository;
import com.kpilszak.touroperator.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TourService {
    private TourPackageRepository tourPackageRepository;
    private TourRepository tourRepository;

    @Autowired
    public TourService(TourPackageRepository tourPackageRepository, TourRepository tourRepository) {
        this.tourPackageRepository = tourPackageRepository;
        this.tourRepository = tourRepository;
    }

    public Tour createTour(String title, String tourPackageName, Map<String, String> details) {
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName).orElseThrow(() ->
            new RuntimeException("Tour package does not exist: " + tourPackageName)
        );
        return tourRepository.save(new Tour(title, tourPackage, details));
    }

    public Iterable<Tour> lookup() {
        return tourRepository.findAll();
    }

    public long total() {
        return tourRepository.count();
    }
}
