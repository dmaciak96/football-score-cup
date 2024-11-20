package com.example.repository;

import com.example.exeption.MatchNotFoundException;
import com.example.exeption.MatchValidationException;
import com.example.model.Match;
import com.example.model.MatchUpdateRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScoreBoardRepositoryInMemoryImpl implements ScoreBoardRepository {

    private final List<Match> matches;

    public ScoreBoardRepositoryInMemoryImpl() {
        this.matches = new ArrayList<>();
    }

    protected ScoreBoardRepositoryInMemoryImpl(List<Match> matches) {
        this.matches = new ArrayList<>(matches);
    }

    @Override
    public Match create(Match match) {
        validateMatch(match);
        matches.add(match);
        return match;
    }

    @Override
    public Match update(MatchUpdateRequest matchUpdateRequest) {
        validateScore(matchUpdateRequest.homeTeamScore(), matchUpdateRequest.awayTeamScore());
        var matchToUpdate = getMatchById(matchUpdateRequest.id());
        matchToUpdate.setAwayTeamScore(matchUpdateRequest.awayTeamScore());
        matchToUpdate.setHomeTeamScore(matchUpdateRequest.homeTeamScore());
        return matchToUpdate;
    }

    private void validateMatch(Match match) {
        if (match.getAwayTeamName() == null || match.getAwayTeamName().isBlank()) {
            throw new MatchValidationException("Away team name is required");
        }

        if (match.getHomeTeamName() == null || match.getHomeTeamName().isBlank()) {
            throw new MatchValidationException("Home team name is required");
        }

        validateScore(match.getHomeTeamScore(), match.getAwayTeamScore());
    }

    private void validateScore(int homeTeamScore, int awayTeamScore) {
        if (homeTeamScore < 0) {
            throw new MatchValidationException("Home team score is required and must be greater or equals 0");
        }

        if (awayTeamScore < 0) {
            throw new MatchValidationException("Away team score is required and must be greater or equals 0");
        }
    }

    private Match getMatchById(UUID id) {
        return matches.stream()
                .filter(match -> match.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new MatchNotFoundException(id));
    }

    @Override
    public boolean deleteById(UUID id) {
        return matches.removeIf(currentMatch -> currentMatch.getId() == id);
    }

    @Override
    public List<Match> getAll() {
        return List.copyOf(matches);
    }
}
