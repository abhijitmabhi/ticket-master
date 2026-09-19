package com.ama.ticketmaster.entity;

import com.ama.ticketmaster.entity.enums.SeatStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "event_seats", uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "seat_id"}))
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @OneToMany(mappedBy = "eventSeat", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @Builder.Default
    private Set<Booking> bookings = new HashSet<>();

    @Version
    private Long version;

    public void markBooked() {
        if (this.status != SeatStatus.AVAILABLE) {
            throw new IllegalArgumentException("Cannot book seat %d: current status is %s".formatted(id, status));
        }
        this.status = SeatStatus.BOOKED;
    }

    public void markAvailable() {
        if (this.status != SeatStatus.BOOKED && this.status != SeatStatus.HELD) {
            throw new IllegalArgumentException("Cannot release seat %d: current status is %s".formatted(id, status));
        }
        this.status = SeatStatus.AVAILABLE;
    }
}
