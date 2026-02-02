package by.smarttour.booking.controller;

import by.smarttour.booking.model.Booking;
import by.smarttour.booking.repository.BookingRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;

    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @PostMapping
    public Booking createBooking(
            @RequestHeader("X-User-Email") String email,
            @RequestBody Long tourId
    ) {
        Booking booking = new Booking();
        booking.setUserEmail(email);
        booking.setTourId(tourId);
        booking.setCreatedAt(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    @GetMapping("/my")
    public List<Booking> getMyBookings(@RequestHeader("X-User-Email") String email) {
        System.out.println(">>> Запрос истории заказов для: " + email);
        return bookingRepository.findByUserEmail(email);
    }
}