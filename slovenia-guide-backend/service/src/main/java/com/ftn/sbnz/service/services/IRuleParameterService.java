package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.models.RuleParameter;
import java.util.List;

public interface IRuleParameterService {
    List<RuleParameter> findAll();
    RuleParameter save(RuleParameter ruleParameter);
    void delete(String paramKey);
}
