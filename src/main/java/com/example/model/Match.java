package com.example.model;

import java.time.Instant;

public class Match {

    private final String homeTeamName;
    private int homeTeamScore;
    private final String awayTeamName;
    private int awayTeamScore;
    private final Instant createdAt;

    public Match(String homeTeamName, String awayTeamName) {
        this(homeTeamName, 0, awayTeamName, 0);
    }

    public Match(String homeTeamName, int homeTeamScore, String awayTeamName, int awayTeamScore) {
        this.homeTeamName = homeTeamName;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamName = awayTeamName;
        this.awayTeamScore = awayTeamScore;
        this.createdAt = Instant.now();
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(int awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public int getTotalScore() {
        return homeTeamScore + awayTeamScore;
    }
}
