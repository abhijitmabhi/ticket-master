package com.ama.ticketmaster.repository;

import com.ama.ticketmaster.entity.Booking;
import com.ama.ticketmaster.entity.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByEventSeatIdAndBookingStatusIn(Long eventId, List<BookingStatus> bookingStatuses);
}
