package by.smarttour.tour.controller;

import by.smarttour.tour.model.Tour;
import by.smarttour.tour.repository.TourRepository;
import org.springframework.web.bind.annotation.*;

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

        // 1. Если есть и то, и другое
        if (location != null && maxPrice != null) {
            return tourRepository.findByLocationContainingIgnoreCaseAndPriceLessThanEqual(location, maxPrice);
        }
        // 2. Если только локация
        if (location != null) {
            return tourRepository.findByLocationContainingIgnoreCase(location);
        }
        // 3. Если только цена
        if (maxPrice != null) {
            return tourRepository.findByPriceLessThanEqual(maxPrice);
        }

        return tourRepository.findAll();
    }
}