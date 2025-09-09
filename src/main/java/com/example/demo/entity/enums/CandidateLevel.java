package com.example.demo.entity.enums;

public enum CandidateLevel {
    NHAN_VIEN("Nhân viên"),
    NHOM_TRUONG("Nhóm trưởng"),
    TRUONG_PHONG("Trưởng phòng"),
    PHO_GIAM_DOC("Phó Giám đốc"),
    GIAM_DOC("Giám đốc"),
    OTHER("Khác");

    private final String displayName;

    CandidateLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
