package com.streamline.bikerental.controller;

import com.streamline.bikerental.dto.BookingDTO;
import com.streamline.bikerental.model.Booking;
import com.streamline.bikerental.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO) throws IOException {
        Booking booking = bookingService.createBooking(bookingDTO);
        return ResponseEntity.ok(booking);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO) throws IOException {
        Booking booking = bookingService.updateBooking(id, bookingDTO);
        return ResponseEntity.ok(booking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/history")
    public ResponseEntity<List<BookingDTO>> getBookingHistory(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<BookingDTO> bookings = bookingService.getBookingHistory(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/admin/bookings")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/previous")
    public ResponseEntity<List<BookingDTO>> getPreviousBookings(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<BookingDTO> bookings = bookingService.getPreviousBookings(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/current")
    public ResponseEntity<List<BookingDTO>> getCurrentBookings(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<BookingDTO> bookings = bookingService.getCurrentBookings(userId);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/reorder/{id}")
    public ResponseEntity<Booking> reorderBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO) throws IOException {
        Booking booking = bookingService.reorderBooking(id);
        return ResponseEntity.ok(booking);
    }
}
