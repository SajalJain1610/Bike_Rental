package com.streamline.bikerental.controller;

import com.streamline.bikerental.dto.VehicleDTO;
import com.streamline.bikerental.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/admin/add")
    public ResponseEntity<VehicleDTO> addBike(@RequestBody VehicleDTO vehicleDTO) {
        VehicleDTO savedVehicle = vehicleService.addBike(vehicleDTO);
        return ResponseEntity.ok(savedVehicle);
    }

    @PutMapping("/admin/edit/{id}")
    public ResponseEntity<VehicleDTO> editBike(@PathVariable Long id, @RequestBody VehicleDTO vehicleDTO) {
        VehicleDTO updatedVehicle = vehicleService.editBike(id, vehicleDTO);
        return ResponseEntity.ok(updatedVehicle);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Void> deleteBike(@PathVariable Long id) {
        vehicleService.deleteBike(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<VehicleDTO>> getAllBikes() {
        List<VehicleDTO> vehicles = vehicleService.getAllBikes();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/admin/status/booked")
    public ResponseEntity<List<VehicleDTO>> getBikesWithStatusBooked() {
        List<VehicleDTO> bookedVehicles = vehicleService.getBikesByStatus("booked");
        return ResponseEntity.ok(bookedVehicles);
    }

    @GetMapping("/admin/status/notBooked")
    public ResponseEntity<List<VehicleDTO>> getBikesWithStatusNotBooked() {
        List<VehicleDTO> notBookedVehicles = vehicleService.getBikesByStatus("notBooked");
        return ResponseEntity.ok(notBookedVehicles);
    }
}
