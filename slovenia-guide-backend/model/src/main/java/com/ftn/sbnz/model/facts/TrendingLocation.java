package com.ftn.sbnz.model.facts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrendingLocation {
    private String locationName;
    private UUID locationId;
    private int visitCount;
    private List<String> topTags;
}