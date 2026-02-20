package com.example.githubchallengemvp.data.model;

public class Repository {
    private String name;
    private String description;
    private int stargazers_count;
    private Owner owner;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStars() {
        return stargazers_count;
    }

    public Owner getOwner() {
        return owner;
    }
}
