package com.ama.ticketmaster.service;

import com.ama.ticketmaster.entity.EventSeat;
import com.ama.ticketmaster.exception.NotFoundException;
import com.ama.ticketmaster.repository.EventSeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventSeatService {
    private final EventSeatRepository eventSeatRepository;

    public EventSeatService(EventSeatRepository eventSeatRepository) {
        this.eventSeatRepository = eventSeatRepository;
    }

    @Transactional(readOnly = true)
    public EventSeat getEventSeatById(Long id) {
        return eventSeatRepository.findById(id).orElseThrow(() -> new NotFoundException("EventSeat not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<EventSeat> getAllEventSeats() {
        return eventSeatRepository.findAll();
    }
}
