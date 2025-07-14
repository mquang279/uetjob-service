package com.example.demo.entity.enums;

public enum JobLevel {
    INTERN("Intern"),
    FRESHER("Fresher"),
    JUNIOR("Junior"),
    SENIOR("Senior"),
    LEAD("Lead"),
    OTHER("Other");

    private final String displayName;

    JobLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
