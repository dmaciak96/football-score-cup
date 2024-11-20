package com.example.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Match {

    private final UUID id;
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
        this.id = UUID.randomUUID();
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

    public UUID getId() {
        return id;
    }

    public int getTotalScore() {
        return homeTeamScore + awayTeamScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(id, match.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "%s %s - %s %s".formatted(homeTeamName, homeTeamScore, awayTeamName, awayTeamScore);
    }
}
