package by.smarttour.booking.client;

import by.smarttour.booking.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "tour-service",
        url = "http://localhost:8082",
        configuration = FeignConfig.class // Добавили эту ссылку
)
public interface TourClient {
    @GetMapping("/api/v1/tours/{id}")
    void getTourById(@PathVariable("id") Long id);

    @PostMapping("/api/v1/tours/{id}/book") // Новый метод
    void bookPlace(@PathVariable("id") Long id);
}