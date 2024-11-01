package com.springbootsoap.soap.endpoint;

import com.springbootsoap.soap.model.Vehicle;
import com.springbootsoap.soap.repository.VehicleRepository;
import com.springbootsoap.soap.VehicleDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.springbootsoap.soap.AddVehicleRequest;
import com.springbootsoap.soap.AddVehicleResponse;
import com.springbootsoap.soap.ListVehiclesRequest;
import com.springbootsoap.soap.ListVehiclesResponse;
import com.springbootsoap.soap.SearchVehicleByBrandRequest;
import com.springbootsoap.soap.SearchVehicleByBrandResponse;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class VehicleEndpoint {

    private static final String NAMESPACE_URI = "http://springbootsoap.com/soap";

    @Autowired
    private VehicleRepository vehicleRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addVehicleRequest")
    @ResponsePayload
    public AddVehicleResponse addVehicle(@RequestPayload AddVehicleRequest request) {
        Vehicle vehicle = new Vehicle(request.getVehicle().getBrand(), request.getVehicle().getModel(), request.getVehicle().getColor());
        vehicleRepository.save(vehicle);
        AddVehicleResponse response = new AddVehicleResponse();
        response.setStatus("Vehicle added successfully");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listVehiclesRequest")
    @ResponsePayload
    public ListVehiclesResponse listVehicles(@RequestPayload ListVehiclesRequest request) {
        ListVehiclesResponse response = new ListVehiclesResponse();
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<VehicleDetails> vehicleDetailsList = vehicles.stream()
                .map(this::convertToVehicleDetails)
                .collect(Collectors.toList());
        response.getVehicles().addAll(vehicleDetailsList);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "searchVehicleByBrandRequest")
    @ResponsePayload
    public SearchVehicleByBrandResponse searchVehicleByBrand(@RequestPayload SearchVehicleByBrandRequest request) {
        SearchVehicleByBrandResponse response = new SearchVehicleByBrandResponse();
        List<Vehicle> vehicles = vehicleRepository.findByBrand(request.getBrand());
        List<VehicleDetails> vehicleDetailsList = vehicles.stream()
                .map(this::convertToVehicleDetails)
                .collect(Collectors.toList());
        response.getVehicles().addAll(vehicleDetailsList);
        return response;
    }

    private VehicleDetails convertToVehicleDetails(Vehicle vehicle) {
        VehicleDetails details = new VehicleDetails();
        details.setBrand(vehicle.getBrand());
        details.setModel(vehicle.getModel());
        details.setColor(vehicle.getColor());
        return details;
    }
}