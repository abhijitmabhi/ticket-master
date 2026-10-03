package com.ama.ticketmaster.service;

import com.ama.ticketmaster.entity.Event;
import com.ama.ticketmaster.entity.EventSeat;
import com.ama.ticketmaster.entity.Seat;
import com.ama.ticketmaster.exception.NotFoundException;
import com.ama.ticketmaster.repository.EventSeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventSeatServiceTest {
    @Mock
    private EventSeatRepository eventSeatRepository;

    @Mock
    private EventService eventService;

    @Mock
    private SeatService seatService;

    @InjectMocks
    private EventSeatService underTest;

    @Test
    void shouldReturnEventSeatById() {
        // arrange
        EventSeat eventSeat = EventSeat.builder()
                .id(1L)
                .event(Event.builder().id(1L).build())
                .seat(Seat.builder().id(1L).build())
                .build();

        when(eventSeatRepository.findById(1L)).thenReturn(Optional.of(eventSeat));

        // act
        EventSeat result = underTest.getEventSeatById(1L);

        // assert
        assertNotNull(result);
        assertNotNull(result.getEvent());
        assertNotNull(result.getSeat());
    }

    @Test
    void shouldThrowExceptionWhenEventSeatIsNotFound() {
        // arrange
        when(eventSeatRepository.findById(1L)).thenReturn(Optional.empty());

        // act & assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> underTest.getEventSeatById(1L));
        assertEquals("EventSeat not found with id: 1", exception.getMessage());
    }

    @Test
    void shouldReturnAllEventSeat() {
        // arrange
        List<EventSeat> eventSeats = List.of(
                EventSeat.builder()
                        .id(1L)
                        .event(Event.builder().id(1L).build())
                        .seat(Seat.builder().id(1L).build())
                        .build(),
                EventSeat.builder()
                        .id(2L)
                        .event(Event.builder().id(1L).build())
                        .seat(Seat.builder().id(2L).build())
                        .build()
        );

        when(eventSeatRepository.findAll()).thenReturn(eventSeats);

        // act

        List<EventSeat> eventSeat = underTest.getAllEventSeats();

        // assert
        assertEquals(2, eventSeat.size());
    }

    @Test
    void shouldReturnEmptyArrayWhenNoSeatEvents() {
        // arrange
        when(eventSeatRepository.findAll()).thenReturn(List.of());

        // act
        List<EventSeat> result = underTest.getAllEventSeats();

        // assert
        assertTrue(result.isEmpty());
    }
}