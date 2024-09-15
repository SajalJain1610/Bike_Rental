package com.streamline.bikerental.controller;

import com.streamline.bikerental.dto.StatisticsDTO;
import com.streamline.bikerental.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/total-users")
    public ResponseEntity<Long> getTotalUsers() {
        long totalUsers = statisticsService.getTotalUsers();
        return ResponseEntity.ok(totalUsers);
    }

    @GetMapping("/total-bikes")
    public ResponseEntity<Long> getTotalBikes() {
        long totalBikes = statisticsService.getTotalBikes();
        return ResponseEntity.ok(totalBikes);
    }

    @GetMapping("/total-bookings")
    public ResponseEntity<Long> getTotalBookings() {
        long totalBookings = statisticsService.getTotalBookings();
        return ResponseEntity.ok(totalBookings);
    }

    @GetMapping("/total-active-bookings")
    public ResponseEntity<Long> getTotalActiveBookings() {
        long totalActiveBookings = statisticsService.getTotalActiveBookings();
        return ResponseEntity.ok(totalActiveBookings);
    }

    @GetMapping("/total-previous-bookings")
    public ResponseEntity<Long> getTotalPreviousBookings() {
        long totalPreviousBookings = statisticsService.getTotalPreviousBookings();
        return ResponseEntity.ok(totalPreviousBookings);
    }

    @GetMapping
    public ResponseEntity<StatisticsDTO> getStatistics() {
        StatisticsDTO statisticsDTO = statisticsService.getStatistics();
        return ResponseEntity.ok(statisticsDTO);
    }
}
