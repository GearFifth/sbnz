package com.ftn.sbnz.service.controllers;

import com.ftn.sbnz.model.models.RuleParameter;
import com.ftn.sbnz.service.services.IRuleParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rule-parameters")
@RequiredArgsConstructor
public class RuleParameterController {

    private final IRuleParameterService service;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<RuleParameter>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RuleParameter> save(@RequestBody RuleParameter ruleParameter) {
        return ResponseEntity.ok(service.save(ruleParameter));
    }

    @DeleteMapping("/{paramKey}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String paramKey) {
        service.delete(paramKey);
        return ResponseEntity.noContent().build();
    }
}
