package com.ftn.sbnz.model.events;

import com.ftn.sbnz.model.enums.RoadStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoadStatusEvent {
    private String roadName;
    private RoadStatus status;
    private Date timestamp;
}