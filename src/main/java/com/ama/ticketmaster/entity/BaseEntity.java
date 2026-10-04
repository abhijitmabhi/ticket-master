package com.ama.ticketmaster.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Generated;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Generated
@Setter
public class BaseEntity {
    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
