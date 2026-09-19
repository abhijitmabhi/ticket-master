package com.ama.ticketmaster.service;

import com.ama.ticketmaster.dto.VenueRequestDto;
import com.ama.ticketmaster.dto.VenueResponseDto;
import com.ama.ticketmaster.entity.Venue;
import com.ama.ticketmaster.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {
    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public void createVenue(VenueRequestDto venueRequest) {
        venueRepository.save(venueRequest.toEntity());
    }

    public VenueResponseDto getVenueById(Long venueId) {
        Venue venue = venueRepository.findById(venueId).orElseThrow();
        return VenueResponseDto.of(venue);
    }

    public List<VenueResponseDto> getAllVenue() {
        List<Venue> allVenues = venueRepository.findAll();
        return allVenues.stream().map(VenueResponseDto::of).toList();
    }
}
