package com.streamline.bikerental.service;

import com.streamline.bikerental.dto.StatisticsDTO;
import com.streamline.bikerental.repository.BookingRepository;
import com.streamline.bikerental.repository.UserRepository;
import com.streamline.bikerental.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public long getTotalUsers() {
        return userRepository.count();
    }

    public long getTotalBikes() {
        return vehicleRepository.count();
    }

    public long getTotalBookings() {
        return bookingRepository.count();
    }

    public long getTotalActiveBookings() {
        return bookingRepository.countActiveBookings();
    }

    public long getTotalPreviousBookings() {
        return bookingRepository.countPreviousBookings();
    }

    public StatisticsDTO getStatistics() {
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setTotalUsers(getTotalUsers());
        statisticsDTO.setTotalBikes(getTotalBikes());
        statisticsDTO.setTotalBookings(getTotalBookings());
        statisticsDTO.setTotalActiveBookings(getTotalActiveBookings());
        statisticsDTO.setTotalPreviousBookings(getTotalPreviousBookings());
        return statisticsDTO;
    }
}
