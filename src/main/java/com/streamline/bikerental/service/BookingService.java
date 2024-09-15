package com.streamline.bikerental.service;

import com.streamline.bikerental.dto.BookingDTO;
import com.streamline.bikerental.exception.ResourceNotFoundException;
import com.streamline.bikerental.model.Booking;
import com.streamline.bikerental.model.User;
import com.streamline.bikerental.model.Vehicle;
import com.streamline.bikerental.repository.BookingRepository;
import com.streamline.bikerental.repository.UserRepository;
import com.streamline.bikerental.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public Booking createBooking(BookingDTO bookingDTO) throws IOException {
        User user = userRepository.findById(bookingDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + bookingDTO.getUserId()));
        Vehicle vehicle = vehicleRepository.findById(bookingDTO.getVehicleId()).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + bookingDTO.getVehicleId()));
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setVehicle(vehicle);
        booking.setStartDate(bookingDTO.getStartDate());
        booking.setEndDate(bookingDTO.getEndDate());
        booking.setStartTime(bookingDTO.getStartTime());
        booking.setEndTime(bookingDTO.getEndTime());
        booking.setQuantityOfBikes(bookingDTO.getQuantityOfBikes());
        booking.setCashPayment(bookingDTO.getCashPayment());
        booking.setOnlinePayment(bookingDTO.getOnlinePayment());
        booking.setEmergencyContactName(bookingDTO.getEmergencyContactName());
        booking.setEmergencyContactPhone(bookingDTO.getEmergencyContactPhone());
        booking.setStatus("booked");
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long bookingId, BookingDTO bookingDTO) throws IOException {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        booking.setStartDate(bookingDTO.getStartDate());
        booking.setEndDate(bookingDTO.getEndDate());
        booking.setStartTime(bookingDTO.getStartTime());
        booking.setEndTime(bookingDTO.getEndTime());
        booking.setQuantityOfBikes(bookingDTO.getQuantityOfBikes());
        booking.setCashPayment(bookingDTO.getCashPayment());
        booking.setOnlinePayment(bookingDTO.getOnlinePayment());
        booking.setEmergencyContactName(bookingDTO.getEmergencyContactName());
        booking.setEmergencyContactPhone(bookingDTO.getEmergencyContactPhone());
        booking.setStatus(bookingDTO.getStatus());
        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        booking.setStatus("canceled");
        bookingRepository.save(booking);
    }

    public List<BookingDTO> getBookingHistory(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        List<Booking> bookings = bookingRepository.findByUser(user);
        return bookings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<BookingDTO> getPreviousBookings(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        List<Booking> bookings = bookingRepository.findByUser(user).stream()
                .filter(booking -> booking.getStatus().equals("completed") || booking.getStatus().equals("canceled"))
                .collect(Collectors.toList());
        return bookings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<BookingDTO> getCurrentBookings(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        List<Booking> bookings = bookingRepository.findByUser(user).stream()
                .filter(booking -> booking.getStatus().equals("booked"))
                .collect(Collectors.toList());
        return bookings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Booking reorderBooking(Long bookingId) throws IOException {
        Booking oldBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        Booking newBooking = new Booking();
        newBooking.setUser(oldBooking.getUser());
        newBooking.setVehicle(oldBooking.getVehicle());
        newBooking.setStartDate(oldBooking.getStartDate());
        newBooking.setEndDate(oldBooking.getEndDate());
        newBooking.setStartTime(oldBooking.getStartTime());
        newBooking.setEndTime(oldBooking.getEndTime());
        newBooking.setQuantityOfBikes(oldBooking.getQuantityOfBikes());
        newBooking.setCashPayment(oldBooking.getCashPayment());
        newBooking.setOnlinePayment(oldBooking.getOnlinePayment());
        newBooking.setEmergencyContactName(oldBooking.getEmergencyContactName());
        newBooking.setEmergencyContactPhone(oldBooking.getEmergencyContactPhone());
        newBooking.setStatus("booked");
        return bookingRepository.save(newBooking);
    }

    private BookingDTO convertToDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setUserId(booking.getUser().getId());
        bookingDTO.setVehicleId(booking.getVehicle().getId());
        bookingDTO.setStartDate(booking.getStartDate());
        bookingDTO.setEndDate(booking.getEndDate());
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setQuantityOfBikes(booking.getQuantityOfBikes());
        bookingDTO.setCashPayment(booking.getCashPayment());
        bookingDTO.setOnlinePayment(booking.getOnlinePayment());
        bookingDTO.setEmergencyContactName(booking.getEmergencyContactName());
        bookingDTO.setEmergencyContactPhone(booking.getEmergencyContactPhone());
        bookingDTO.setStatus(booking.getStatus());
        return bookingDTO;
    }
}
