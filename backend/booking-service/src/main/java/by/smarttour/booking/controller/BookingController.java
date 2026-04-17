package by.smarttour.booking.controller;

import by.smarttour.booking.client.TourClient;
import by.smarttour.booking.model.Booking;
import by.smarttour.booking.repository.BookingRepository;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final TourClient tourClient;

    public BookingController(BookingRepository bookingRepository, TourClient tourClient) {
        this.bookingRepository = bookingRepository;
        this.tourClient = tourClient;
    }

    @PostMapping
    public Booking createBooking(@RequestHeader("X-User-Email") String email, @RequestBody Long tourId) {
        try {
            // 1. Проверяем, существует ли тур
            tourClient.getTourById(tourId);

            // 2. Списываем одно место в Тур-сервисе
            tourClient.bookPlace(tourId);

        } catch (feign.FeignException e) {
            if (e.status() == 404) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Тур не найден");
            if (e.status() == 400) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Места закончились!");
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Ошибка связи с сервером");
        }

        // 3. Если всё ок — сохраняем бронь
        Booking booking = new Booking();
        booking.setUserEmail(email);
        booking.setTourId(tourId);
        booking.setCreatedAt(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

    @GetMapping("/my")
    public List<Booking> getMyBookings(@RequestHeader("X-User-Email") String email) {
        return bookingRepository.findByUserEmail(email);
    }
}