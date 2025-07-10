package com.example.demo.entity.enums;

public enum JobCategory {
    TECHNOLOGY("Technology"),
    FINANCE("Finance"),
    HEALTHCARE("Healthcare"),
    EDUCATION("Education"),
    MARKETING("Marketing"),
    SALES("Sales"),
    HUMAN_RESOURCES("Human Resources"),
    OPERATIONS("Operations"),
    CUSTOMER_SERVICE("Customer Service"),
    ENGINEERING("Engineering"),
    DESIGN("Design"),
    CONSULTING("Consulting"),
    MANUFACTURING("Manufacturing"),
    RETAIL("Retail"),
    HOSPITALITY("Hospitality"),
    OTHER("Other");

    private final String displayName;

    JobCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
