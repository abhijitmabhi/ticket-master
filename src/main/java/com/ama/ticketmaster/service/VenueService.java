package com.ama.ticketmaster.service;

import com.ama.ticketmaster.dto.venue.VenueRequestDto;
import com.ama.ticketmaster.dto.venue.VenueResponseDto;
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

    public Venue getVenueById(Long venueId) {
        return venueRepository.findById(venueId).orElseThrow();
    }

    public List<VenueResponseDto> getAllVenue() {
        List<Venue> allVenues = venueRepository.findAll();
        return allVenues.stream().map(VenueResponseDto::of).toList();
    }
}
