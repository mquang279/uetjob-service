package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.entity.Skill;
import com.example.demo.exception.SkillNotFoundException;
import com.example.demo.repository.SkillRepository;
import com.example.demo.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public PaginationResponse<Skill> getAllSkills(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Skill> skills = this.skillRepository.findAll(pageable);
        PaginationResponse<Skill> response = new PaginationResponse<>(skills);
        return response;
    }

    @Override
    public Skill createSkill(Skill skill) {
        return this.skillRepository.save(skill);
    }

    @Override
    public Skill getSkillById(Long id) {
        Optional<Skill> skillOptional = this.skillRepository.findById(id);
        return skillOptional.orElseThrow(() -> new SkillNotFoundException(id));
    }

    @Override
    public Skill updateSkill(Long id, Skill skill) {
        Skill existingSkill = this.getSkillById(id);
        if (skill.getName() != null) {
            existingSkill.setName(skill.getName());
        }
        return this.skillRepository.save(existingSkill);
    }

}
