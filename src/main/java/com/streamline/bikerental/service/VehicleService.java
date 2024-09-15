package com.streamline.bikerental.service;

import com.streamline.bikerental.dto.VehicleDTO;
import com.streamline.bikerental.exception.ResourceNotFoundException;
import com.streamline.bikerental.model.Vehicle;
import com.streamline.bikerental.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public VehicleDTO addBike(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();
        vehicle.setBikeName(vehicleDTO.getBikeName());
        vehicle.setBikeDescription(vehicleDTO.getBikeDescription());
        vehicle.setRegistrationNumber(vehicleDTO.getRegistrationNumber());
        vehicle.setStatus(vehicleDTO.getStatus());
        vehicle.setRatePerDay(vehicleDTO.getRatePerDay());
        vehicle.setImageUrl(vehicleDTO.getImageUrl());

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return convertToDTO(savedVehicle);
    }

    public void deleteBike(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        vehicleRepository.delete(vehicle);
    }

    public VehicleDTO editBike(Long id, VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));

        vehicle.setBikeName(vehicleDTO.getBikeName());
        vehicle.setBikeDescription(vehicleDTO.getBikeDescription());
        vehicle.setRegistrationNumber(vehicleDTO.getRegistrationNumber());
        vehicle.setStatus(vehicleDTO.getStatus());
        vehicle.setRatePerDay(vehicleDTO.getRatePerDay());
        vehicle.setImageUrl(vehicleDTO.getImageUrl());

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return convertToDTO(updatedVehicle);
    }

    public List<VehicleDTO> getAllBikes() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<VehicleDTO> getBikesByStatus(String status) {
        List<Vehicle> vehicles = vehicleRepository.findByStatus(status);
        return vehicles.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private VehicleDTO convertToDTO(Vehicle vehicle) {
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(vehicle.getId());
        vehicleDTO.setBikeName(vehicle.getBikeName());
        vehicleDTO.setBikeDescription(vehicle.getBikeDescription());
        vehicleDTO.setRegistrationNumber(vehicle.getRegistrationNumber());
        vehicleDTO.setStatus(vehicle.getStatus());
        vehicleDTO.setRatePerDay(vehicle.getRatePerDay());
        vehicleDTO.setImageUrl(vehicle.getImageUrl());
        return vehicleDTO;
    }
}
