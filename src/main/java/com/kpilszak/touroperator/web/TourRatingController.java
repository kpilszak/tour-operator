package com.kpilszak.touroperator.web;

import com.kpilszak.touroperator.domain.TourRating;
import com.kpilszak.touroperator.repo.TourRatingRepository;
import com.kpilszak.touroperator.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.AbstractMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;

@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
    TourRatingRepository tourRatingRepository;
    TourRepository tourRepository;

    @Autowired

    public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    protected TourRatingController() {

    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") String tourId, @RequestBody @Valid TourRating tourRating) {
        verifyTour(tourId);
        tourRatingRepository.save(new TourRating(tourId, tourRating.getCustomerId(), tourRating.getScore(), tourRating.getComment()));
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<TourRating> getAllRatingfForTour(@PathVariable(value = "tourId") String tourId, Pageable pageable) {
        return tourRatingRepository.findAllByTourId(tourId, pageable);
    }

    @RequestMapping(method = RequestMethod.GET, path="/average")
    public AbstractMap.SimpleEntry<String, Double> getAverage(@PathVariable(value = "tourId") String tourId) {
        verifyTour(tourId);
        List<TourRating> tourRatings = tourRatingRepository.findAllByTourId(tourId);
        OptionalDouble average = tourRatings.stream().mapToInt(TourRating::getScore).average();
        return new AbstractMap.SimpleEntry<String, Double>("average", average.isPresent() ? average.getAsDouble() : null);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public TourRating updateWithPut(@PathVariable(value = "tourId") String tourId, @RequestBody @Valid TourRating tourRating) {
        TourRating rating = verifyTourRating(tourId, tourRating.getCustomerId());
        tourRating.setScore(tourRating.getScore());
        tourRating.setComment(tourRating.getComment());
        return tourRatingRepository.save(rating);
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public TourRating updateWithPatch(@PathVariable(value = "tourId") String tourId, @RequestBody @Valid TourRating tourRating) {
        TourRating rating = verifyTourRating(tourId, tourRating.getCustomerId());
        if (tourRating.getScore() != null) {
            tourRating.setScore(rating.getScore());
        }
        if (tourRating.getComment() != null) {
            tourRating.setComment(rating.getComment());
        }
        return tourRatingRepository.save(rating);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{customerId}")
    public void delete(@PathVariable(value = "tourId") String tourId, @PathVariable(value = "customerId") int customerId) {
        TourRating rating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(rating);
    }

    private TourRating verifyTourRating(String tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId).orElseThrow(() ->
                new NoSuchElementException("Tour-Rating pair for request("
                        + tourId + " for customer " + customerId + ")"));
    }

    private void verifyTour(String tourId) throws NoSuchElementException {
        if (!tourRepository.existsById(tourId)) {
                throw new NoSuchElementException("Tour does not exist " + tourId);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
