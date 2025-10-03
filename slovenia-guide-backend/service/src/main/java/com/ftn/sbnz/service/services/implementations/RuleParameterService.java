package com.ftn.sbnz.service.services.implementations;

import com.ftn.sbnz.model.models.RuleParameter;
import com.ftn.sbnz.service.exceptions.EntityNotFoundException;
import com.ftn.sbnz.service.repositories.IRuleParameterRepository;
import com.ftn.sbnz.service.services.IRuleParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleParameterService implements IRuleParameterService {

    private final IRuleParameterRepository repository;

    @Override
    public List<RuleParameter> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public RuleParameter save(RuleParameter ruleParameter) {
        // This method handles both create and update (upsert)
        return repository.save(ruleParameter);
    }

    @Override
    @Transactional
    public void delete(String paramKey) {
        if (!repository.existsById(paramKey)) {
            throw new EntityNotFoundException("Parameter with key '" + paramKey + "' not found.");
        }
        repository.deleteById(paramKey);
    }
}
