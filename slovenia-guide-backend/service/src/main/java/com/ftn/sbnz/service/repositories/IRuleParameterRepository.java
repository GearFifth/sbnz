package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.models.RuleParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRuleParameterRepository extends JpaRepository<RuleParameter, String> {
}