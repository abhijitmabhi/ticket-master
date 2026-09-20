package com.ama.ticketmaster.exception;

public class VenueSchedulingConflictException extends RuntimeException {
    public VenueSchedulingConflictException(String message) {
        super(message);
    }
}
