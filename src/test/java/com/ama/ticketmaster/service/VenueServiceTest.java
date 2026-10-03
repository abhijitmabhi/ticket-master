package com.ama.ticketmaster.service;

import com.ama.ticketmaster.dto.venue.VenueRequestDto;
import com.ama.ticketmaster.dto.venue.VenueResponseDto;
import com.ama.ticketmaster.entity.Venue;
import com.ama.ticketmaster.repository.VenueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VenueServiceTest {
    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private VenueService underTest;

    @Test
    public void shouldCreateVenue() {
        // arrange
        VenueRequestDto requestDto = new VenueRequestDto("Olympiad, Berlin");

        // act
        underTest.createVenue(requestDto);

        // assert
        ArgumentCaptor<Venue> venueArgumentCaptor = ArgumentCaptor.forClass(Venue.class);

        verify(venueRepository).save(venueArgumentCaptor.capture());
        assertEquals(requestDto.address(), venueArgumentCaptor.getValue().getAddress());
    }

    @Test
    public void shouldReturnVenue() {
        // arrange
        Long venueId = 1L;
        Venue venue = Venue.builder().id(venueId).address("Olympiad, Berlin").build();

        when(venueRepository.findById(venueId)).thenReturn(Optional.of(venue));

        // act
        Venue result = underTest.getVenueById(venueId);

        // assert
        assertEquals(venue, result);
    }

    @Test
    public void shouldThrowExceptionWhenVenueNotFound() {
        when(venueRepository.findById(any())).thenThrow(new NoSuchElementException("Venue not found"));

        assertThatThrownBy(() -> underTest.getVenueById(any()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Venue not found");
    }

    @Test
    public void shouldReturnAllVenues() {
        Venue venue1 = Venue.builder().id(1L).address("Olympiad, Berlin").build();

        Venue venue2 = Venue.builder().id(2L).address("Marzahn, Berlin").build();

        when(venueRepository.findAll()).thenReturn(List.of(venue1, venue2));

        List<VenueResponseDto> result = underTest.getAllVenue();

        assertThat(result).isEqualTo(List.of(VenueResponseDto.of(venue1), VenueResponseDto.of(venue2)));
    }
}
