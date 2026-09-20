package com.ama.ticketmaster.dto.venue;

import com.ama.ticketmaster.entity.Venue;

public record VenueRequestDto(String address) {
    public Venue toEntity() {
        return Venue.builder().address(address).build();
    }
}
