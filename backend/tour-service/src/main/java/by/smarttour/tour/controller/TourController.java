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
            @RequestParam(required = false) String location // Добавляем этот параметр
    ) {
        if (location != null && !location.isEmpty()) {
            return tourRepository.findByLocationContainingIgnoreCase(location);
        }
        return tourRepository.findAll();
    }
}