package com.example.demo.entity.enums;

public enum ResumeStatus {
    REVIEWING("Reviewing"),
    ACCEPTED("Accepted"),
    REJECTED("Junior");

    private final String displayName;

    ResumeStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
