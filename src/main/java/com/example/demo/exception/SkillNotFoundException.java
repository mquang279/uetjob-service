package com.example.demo.exception;

public class SkillNotFoundException extends RuntimeException {
    public SkillNotFoundException() {
        super("Skill not found");
    }

    public SkillNotFoundException(Long id) {
        super("Skill with id " + id + " not found");
    }

    public SkillNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
