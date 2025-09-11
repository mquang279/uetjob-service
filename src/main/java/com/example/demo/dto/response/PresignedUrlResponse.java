package com.example.demo.dto.response;

public class PresignedUrlResponse {
    private String url;
    private String objectKey;

    public PresignedUrlResponse() {
    }

    public PresignedUrlResponse(String url, String objectKey) {
        this.url = url;
        this.objectKey = objectKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getObJectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }
}
