package com.example.repository;

import com.example.exeption.MatchNotFoundException;
import com.example.model.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        matches.add(match);
        return match;
    }

    @Override
    public Match update(Match match) {
        var matchToUpdate = getMatchById(match.getId());
        if (matchToUpdate.isEmpty()) {
            throw new MatchNotFoundException(match.getId());
        }
        matchToUpdate.get().setAwayTeamScore(match.getAwayTeamScore());
        matchToUpdate.get().setHomeTeamScore(match.getHomeTeamScore());
        matches.set(matches.indexOf(match), matchToUpdate.get());
        return matchToUpdate.get();
    }

    private Optional<Match> getMatchById(UUID id) {
        return matches.stream()
                .filter(match -> match.getId().equals(id))
                .findFirst();
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
