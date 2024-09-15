package com.streamline.bikerental.repository;

import com.streamline.bikerental.model.Booking;
import com.streamline.bikerental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
    long count();

    long countByStatus(String status);

    default long countActiveBookings() {
        return countByStatus("booked");
    }

    default long countPreviousBookings() {
        return countByStatus("completed") + countByStatus("canceled");
    }
}