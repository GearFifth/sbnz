package com.ftn.sbnz.model.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RuleParameter {
    @Id
    private String paramKey;

    private double paramValue;
}