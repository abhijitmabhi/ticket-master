package com.ama.ticketmaster.service;

import com.ama.ticketmaster.dto.event.EventRequestDto;
import com.ama.ticketmaster.entity.Event;
import com.ama.ticketmaster.entity.Venue;
import com.ama.ticketmaster.exception.VenueSchedulingConflictException;
import com.ama.ticketmaster.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {
    @Mock
    private EventRepository eventRepository;

    @Mock
    private VenueService venueService;

    @InjectMocks
    private EventService underTest;

    @Test
    void shouldCreateEvent() {
        // arrange
        EventRequestDto requestDto = new EventRequestDto(
                "Event Name",
                Instant.parse("2023-01-01T00:00:00Z"),
                Instant.parse("2023-01-02T00:00:00Z"),
                1L
        );

        Venue venue = mock(Venue.class);
        when(venue.getId()).thenReturn(1L);

        when(venueService.getVenueById(1L)).thenReturn(venue);


        // act
        underTest.createEvent(requestDto);

        // assert
        ArgumentCaptor<Event> venueArgumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(venueArgumentCaptor.capture());
        assertEquals(requestDto.name(), venueArgumentCaptor.getValue().getName());
        assertEquals(requestDto.startTime(), venueArgumentCaptor.getValue().getStartTime());
        assertEquals(requestDto.endTime(), venueArgumentCaptor.getValue().getEndTime());
        assertEquals(venue, venueArgumentCaptor.getValue().getVenue());
    }

    @Test
    void shouldNotCreateEventWhenStartTimeIsAfterEndTime() {
        // arrange
        Long venueId = 1L;
        EventRequestDto requestDto = new EventRequestDto(
                "Event Name",
                Instant.parse("2023-01-06T00:00:00Z"),
                Instant.parse("2023-01-02T00:00:00Z"),
                venueId
        );

        // assert & act
        assertThrows(IllegalArgumentException.class, () -> underTest.createEvent(requestDto));
        verify(eventRepository, never()).save(any());
    }

    @Test
    void shouldNotCreateEventWhenEventIsConflictWithOtherEvent() {
        // arrange
        EventRequestDto requestDto = new EventRequestDto(
                "Event Name",
                Instant.parse("2023-01-01T00:00:00Z"),
                Instant.parse("2023-01-02T00:00:00Z"),
                1L
        );

        Venue venue = mock(Venue.class);
        when(venue.getId()).thenReturn(1L);

        when(venueService.getVenueById(1L)).thenReturn(venue);

        when(eventRepository.existsOverlappingEvent(any(), any(), any())).thenReturn(true);

        // act & assert
        VenueSchedulingConflictException exception = assertThrows(VenueSchedulingConflictException.class, () -> underTest.createEvent(requestDto));
        assertEquals("Conflict with another event", exception.getMessage());

    }

}