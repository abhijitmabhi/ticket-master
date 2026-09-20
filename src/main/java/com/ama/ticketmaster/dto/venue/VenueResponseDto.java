package com.ama.ticketmaster.dto.venue;

import com.ama.ticketmaster.entity.Venue;

public record VenueResponseDto(Long id, String address) {
    public static VenueResponseDto of(Venue venue) {
        return new VenueResponseDto(venue.getId(), venue.getAddress());
    }
}
