package com.rehancode.user_service.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rehancode.user_service.DTO.AddressRequest;
import com.rehancode.user_service.DTO.AddressResponse;
import com.rehancode.user_service.Entity.AddressEntity;
import com.rehancode.user_service.Exceptions.ResourceNotFoundException;
import com.rehancode.user_service.Repository.AddressRepository;
import com.rehancode.user_service.Repository.UserRepository;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    // Convert Entity -> DTO
    public AddressResponse convertToDTO(AddressEntity address) {
        return AddressResponse.builder()
                .id(address.getId())
                .city(address.getCity())
                .street(address.getStreet())
                .zipCode(address.getZipCode())
                .userId(address.getUser().getId())
                .build();
    }

    // Convert DTO -> Entity
    public AddressEntity convertToEntity(AddressRequest dto) {
        var user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return AddressEntity.builder()
                .city(dto.getCity())
                .street(dto.getStreet())
                .zipCode(dto.getZipCode())
                .user(user)
                .build();
    }

    // Add new address
    public AddressResponse addAddress(AddressRequest request) {
        AddressEntity entity = convertToEntity(request);
        AddressEntity saved = addressRepository.save(entity);
        return convertToDTO(saved);
    }

    // Get address by ID
    public AddressResponse getAddressById(Long id) {
        AddressEntity entity = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        return convertToDTO(entity);
    }

    // Get all addresses
    public List<AddressResponse> getAllAddresses() {
        return addressRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
