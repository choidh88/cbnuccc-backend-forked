package com.cbnuccc.cbnuccc.Dto;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrayerDto {
    Integer id;

    OffsetDateTime createdAt;

    String request;

    Boolean anonymous;
}
