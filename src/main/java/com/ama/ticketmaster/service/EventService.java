package com.ama.ticketmaster.service;

import com.ama.ticketmaster.dto.event.EventRequestDto;
import com.ama.ticketmaster.dto.event.EventResponseDto;
import com.ama.ticketmaster.entity.Venue;
import com.ama.ticketmaster.exception.NotFoundException;
import com.ama.ticketmaster.exception.VenueSchedulingConflictException;
import com.ama.ticketmaster.repository.EventRepository;
import com.ama.ticketmaster.repository.VenueRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventService(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    public EventResponseDto createEvent(EventRequestDto request) {
        Venue venue = venueRepository
                .findById(request.venueId())
                .orElseThrow(() -> new NotFoundException("Venue not found exception with id: " + request.venueId()));

        if (eventRepository.existsOverlappingEvent(venue.getId(), request.startTime(), request.endTime())) {
            throw new VenueSchedulingConflictException("Conflict with another event");
        }

        return EventResponseDto.of(eventRepository.save(request.toEntity(venue)));
    }
}
