package com.ama.ticketmaster.service;

import com.ama.ticketmaster.dto.event.EventRequestDto;
import com.ama.ticketmaster.entity.Venue;
import com.ama.ticketmaster.exception.VenueSchedulingConflictException;
import com.ama.ticketmaster.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final VenueService venueService;

    public EventService(EventRepository eventRepository, VenueService venueService) {
        this.eventRepository = eventRepository;
        this.venueService = venueService;
    }

    public void createEvent(EventRequestDto request) {
        Venue venue = venueService.getVenueById(request.venueId());

        if (!request.startTime().isBefore(request.endTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }


        if (eventRepository.existsOverlappingEvent(venue.getId(), request.startTime(), request.endTime())) {
            throw new VenueSchedulingConflictException("Conflict with another event");
        }

        eventRepository.save(request.toEntity(venue));
    }
}
