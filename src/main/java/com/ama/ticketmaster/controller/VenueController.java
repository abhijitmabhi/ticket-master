package com.ama.ticketmaster.controller;

import com.ama.ticketmaster.dto.VenueRequestDto;
import com.ama.ticketmaster.dto.VenueResponseDto;
import com.ama.ticketmaster.service.VenueService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/venue")
public class VenueController {
    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping
    public ResponseEntity<Void> createVenue(@RequestBody VenueRequestDto venueRequestDto) {
        venueService.createVenue(venueRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<VenueResponseDto>> findAllVenues() {
        return ResponseEntity.ok(venueService.getAllVenue());
    }

    @GetMapping("/{venueId}")
    public ResponseEntity<VenueResponseDto> findVenueById(@PathVariable long venueId) {
        return ResponseEntity.ok(venueService.getVenueById(venueId));
    }
}
