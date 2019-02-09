package com.example.nekretnine.Model;

public class UserAdvertInfo {
    private Long numberOfAdverts;
    private Long numberOfFavorites;
    private Long numberOfAdvertViews;

    public Long getNumberOfAdverts() {
        return numberOfAdverts;
    }
    public void setNumberOfAdverts(Long numberOfAdverts) {
        this.numberOfAdverts = numberOfAdverts;
    }

    public Long getNumberOfFavorites() {
        return numberOfFavorites;
    }
    public void setNumberOfFavorites(Long numberOfFavorites) {
        this.numberOfFavorites = numberOfFavorites;
    }

    public Long getNumberOfAdvertViews() {
        return numberOfAdvertViews;
    }
    public void setNumberOfAdvertViews(Long numberOfAdvertViews) {
        this.numberOfAdvertViews = numberOfAdvertViews;
    }
}
