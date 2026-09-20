package com.ama.ticketmaster.dto.event;

import com.ama.ticketmaster.dto.venue.VenueResponseDto;
import com.ama.ticketmaster.entity.Event;
import java.time.Instant;

public record EventResponseDto(Long id, String name, Instant startTime, Instant endTime, VenueResponseDto venue) {
    public static EventResponseDto of(Event event) {
        return new EventResponseDto(
                event.getId(),
                event.getName(),
                event.getStartTime(),
                event.getEndTime(),
                VenueResponseDto.of(event.getVenue()));
    }
}
