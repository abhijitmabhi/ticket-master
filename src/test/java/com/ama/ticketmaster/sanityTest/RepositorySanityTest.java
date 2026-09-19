package com.ama.ticketmaster.sanityTest;

import com.ama.ticketmaster.entity.Seat;
import com.ama.ticketmaster.entity.Venue;
import com.ama.ticketmaster.repository.SeatRepository;
import com.ama.ticketmaster.repository.VenueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RepositorySanityTest {
    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Test
    @Transactional
    public void saveAndFetchVenueAndSeats() {
        Venue venue = venueRepository.save(Venue.builder()
                .address("Marzahner Promenade")
                .build());

        Seat seat = seatRepository.save(Seat.builder()
                .row("A")
                .seatNumber("1")
                .venue(venue)
                .build());

        Seat fetched = seatRepository.findById(seat.getId())
                .orElseThrow();


        assertThat(fetched.getRow()).isEqualTo("A");
        assertThat(fetched.getSeatNumber()).isEqualTo("1");
        assertThat(fetched.getVenue().getAddress()).isEqualTo("Marzahner Promenade");
    }
}
