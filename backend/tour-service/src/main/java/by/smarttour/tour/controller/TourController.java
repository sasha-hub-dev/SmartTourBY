package by.smarttour.tour.controller;

import by.smarttour.tour.model.Tour;
import by.smarttour.tour.repository.TourRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tours")
public class TourController {

    private final TourRepository tourRepository;

    public TourController(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @GetMapping
    public List<Tour> getAllTours(
            @RequestHeader(value = "X-User-Email", required = false) String email,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double maxPrice // Новый параметр
    ) {
        System.out.println(">>> Поиск: " + location + ", цена до: " + maxPrice);
        if (location != null && maxPrice != null) {
            return tourRepository.findByLocationContainingIgnoreCaseAndPriceLessThanEqual(location, maxPrice);
        }
        if (location != null) {
            return tourRepository.findByLocationContainingIgnoreCase(location);
        }
        if (maxPrice != null) {
            return tourRepository.findByPriceLessThanEqual(maxPrice);
        }
        return tourRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tour> getTourById(@PathVariable Long id) {
        return tourRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Transactional
    public void bookTourPlace(Long tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Маршрут не найден"));

        if (tour.getAvailableSlots() <= 0) {
            // Если мест нет, бросаем исключение, чтобы транзакция откатилась
            throw new RuntimeException("Извините, на маршрут «" + tour.getTitle() + "» мест больше нет!");
        }

        tour.setAvailableSlots(tour.getAvailableSlots() - 1);
        tourRepository.save(tour);
    }
}