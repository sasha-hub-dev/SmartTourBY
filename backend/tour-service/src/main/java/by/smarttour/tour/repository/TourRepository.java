package by.smarttour.tour.repository;

import by.smarttour.tour.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findByLocationContainingIgnoreCase(String location);
    List<Tour> findByPriceLessThanEqual(Double maxPrice);
    List<Tour> findByLocationContainingIgnoreCaseAndPriceLessThanEqual(String location, Double maxPrice);
}



