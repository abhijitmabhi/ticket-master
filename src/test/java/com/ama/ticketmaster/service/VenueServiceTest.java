package com.ama.ticketmaster.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ama.ticketmaster.dto.venue.VenueRequestDto;
import com.ama.ticketmaster.dto.venue.VenueResponseDto;
import com.ama.ticketmaster.entity.Venue;
import com.ama.ticketmaster.repository.VenueRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VenueServiceTest {
    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private VenueService venueServiceTest;

    @Test
    public void shouldCreateVenue() {
        VenueRequestDto requestDto = new VenueRequestDto("Olympiad, Berlin");

        venueServiceTest.createVenue(requestDto);

        verify(venueRepository).save(any(Venue.class));
    }

    @Test
    public void shouldReturnVenue() {
        Long venueId = 1L;
        Venue venue = Venue.builder().id(venueId).address("Olympiad, Berlin").build();

        VenueResponseDto venueResponseDto = VenueResponseDto.of(venue);

        when(venueRepository.findById(venueId)).thenReturn(Optional.of(venue));

        VenueResponseDto result = venueServiceTest.getVenueById(venueId);

        assertThat(result).isEqualTo(venueResponseDto);
    }

    @Test
    public void shouldThrowExceptionWhenVenueNotFound() {
        when(venueRepository.findById(any())).thenThrow(new NoSuchElementException("Venue not found"));

        assertThatThrownBy(() -> venueServiceTest.getVenueById(any()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Venue not found");
    }

    @Test
    public void shouldReturnAllVenues() {
        Venue venue1 = Venue.builder().id(1L).address("Olympiad, Berlin").build();

        Venue venue2 = Venue.builder().id(2L).address("Marzahn, Berlin").build();

        when(venueRepository.findAll()).thenReturn(List.of(venue1, venue2));

        List<VenueResponseDto> result = venueServiceTest.getAllVenue();

        assertThat(result).isEqualTo(List.of(VenueResponseDto.of(venue1), VenueResponseDto.of(venue2)));
    }
}
