package com.veljkobogdan.jauss.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Url {
    @Id
    private String id;
    private String longUrl;
    private String hash;

    public Url() {}

    public Url(String longUrl, String hash) {
        this.longUrl = longUrl;
        this.hash = hash;
    }

    public Url(String id, String longUrl, String hash) {
        this.id = id;
        this.longUrl = longUrl;
        this.hash = hash;
    }

    public String getId() {
        return id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public Url setLongUrl(String longUrl) {
        this.longUrl = longUrl;
        return this;
    }

    public String getHash() {
        return hash;
    }

    public Url setHash(String hash) {
        this.hash = hash;
        return this;
    }
}
