package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.entity.Skill;
import com.example.demo.service.SkillService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("/skills")
    public ResponseEntity<PaginationResponse<Skill>> getAllSkills(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok().body(this.skillService.getAllSkills(page, pageSize));
    }

    @PostMapping("/skills")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.createSkill(skill));
    }

    @PutMapping("skills/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @RequestBody Skill skill) {
        return ResponseEntity.ok().body(this.skillService.updateSkill(id, skill));
    }
}
