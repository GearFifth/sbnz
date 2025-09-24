package com.ftn.sbnz.model.template;

import com.ftn.sbnz.model.enums.Budget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetRuleTemplateModel {
    private Budget budgetCategory;
    private double priceLimit;
}