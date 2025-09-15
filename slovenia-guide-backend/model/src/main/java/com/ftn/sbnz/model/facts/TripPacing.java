package com.ftn.sbnz.model.facts;

import com.ftn.sbnz.model.enums.PacingLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripPacing {
    private PacingLevel level;
}