package com.inglab.klse.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    @Column(name = "record_datetime", nullable = false)
    private LocalDateTime recordDatetime;

    @Column(name = "num_gainers", nullable = false)
    private Integer numGainers;

    @Column(name = "num_losers", nullable = false)
    private Integer numLosers;
}
