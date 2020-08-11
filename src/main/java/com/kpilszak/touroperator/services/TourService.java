package com.kpilszak.touroperator.services;

import com.kpilszak.touroperator.domain.Difficulty;
import com.kpilszak.touroperator.domain.Region;
import com.kpilszak.touroperator.domain.Tour;
import com.kpilszak.touroperator.domain.TourPackage;
import com.kpilszak.touroperator.repo.TourPackageRepository;
import com.kpilszak.touroperator.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TourService {
    private TourPackageRepository tourPackageRepository;
    private TourRepository tourRepository;

    @Autowired
    public TourService(TourPackageRepository tourPackageRepository, TourRepository tourRepository) {
        this.tourPackageRepository = tourPackageRepository;
        this.tourRepository = tourRepository;
    }

    public Tour createTour(String title, String description, String blurb, Integer price, String duration, String bullets,
                String keywords, String tourPackageName, Difficulty difficulty, Region region) {
        Optional<TourPackage> tourPackage = tourPackageRepository.findByName(tourPackageName);
        if (!tourPackage.isPresent()) {
            throw new RuntimeException("Tour package does not exist: " + tourPackageName);
        }

        return tourRepository.save(new Tour(title, description, blurb, price, duration, bullets, keywords, tourPackage.get(),
                difficulty, region));
    }

    public Iterable<Tour> lookup() {
        return tourRepository.findAll();
    }

    public long total() {
        return tourRepository.count();
    }
}
