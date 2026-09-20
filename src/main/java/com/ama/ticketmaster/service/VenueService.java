package com.ama.ticketmaster.service;

import com.ama.ticketmaster.dto.venue.VenueRequestDto;
import com.ama.ticketmaster.dto.venue.VenueResponseDto;
import com.ama.ticketmaster.entity.Venue;
import com.ama.ticketmaster.repository.VenueRepository;
import java.util.List;
import org.springframework.stereotype.Service;

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
