package com.ama.ticketmaster.repository;

import com.ama.ticketmaster.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("""
            SELECT COUNT(e) > 0
            FROM Event e
            WHERE e.venue.id = :venueId
            AND e.startTime < :endTime
            AND :startTime < e.endTime
            """)
    boolean existsOverlappingEvent(@Param("venueId") Long eventId, @Param("startTime") Instant startTime, @Param("endTime") Instant endTime);
}
