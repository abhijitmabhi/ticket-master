package com.ama.ticketmaster.repository;

import com.ama.ticketmaster.entity.EventSeat;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventSeatRepository extends JpaRepository<EventSeat, Long> {
    Optional<EventSeat> findByEventIdAndSeatId(Long eventId, Long seatId);

    boolean existsByEventId(Long eventId);
}
