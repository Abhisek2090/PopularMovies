package com.example.popularmovies.model;

import java.io.Serializable;

public class Trailer implements Serializable {
    private String key;
    private String name;

    public Trailer(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
