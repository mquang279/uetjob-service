package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.entity.Skill;

public interface SkillService {
    PaginationResponse<Skill> getAllSkills(int page, int pageSize);

    Skill getSkillById(Long id);

    Skill createSkill(Skill skill);

    Skill updateSkill(Long id, Skill skill);
}
