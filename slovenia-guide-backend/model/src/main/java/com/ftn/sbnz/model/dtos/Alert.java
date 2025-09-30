package com.ftn.sbnz.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
    private String message;
    private UUID planId;
    private UUID locationId;
}