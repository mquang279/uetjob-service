package com.example.demo.entity.enums;

public enum JobType {
    PART_TIME("Part time"),
    FULL_TIME("Full time");

    private final String displayName;

    JobType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
