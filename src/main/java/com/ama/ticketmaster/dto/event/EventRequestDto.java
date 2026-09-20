package com.ama.ticketmaster.dto.event;

import com.ama.ticketmaster.entity.Event;
import com.ama.ticketmaster.entity.Venue;
import java.time.Instant;

public record EventRequestDto(String name, Instant startTime, Instant endTime, Long venueId) {
    public Event toEntity(Venue venue) {
        return Event.builder()
                .name(name)
                .startTime(startTime)
                .endTime(endTime)
                .venue(venue)
                .build();
    }
}
