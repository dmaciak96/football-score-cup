package com.example.service;

import com.example.exeption.MatchNotFoundException;
import com.example.model.Match;
import com.example.model.MatchUpdateRequest;
import com.example.repository.ScoreBoardRepository;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ScoreBoardServiceImpl implements ScoreBoardService {

    private final ScoreBoardRepository scoreBoardRepository;

    public ScoreBoardServiceImpl(ScoreBoardRepository scoreBoardRepository) {
        this.scoreBoardRepository = scoreBoardRepository;
    }

    @Override
    public Match startGame(String homeTeamName, String awayTeamName) {
        var newMatch = new Match(homeTeamName, awayTeamName);
        return scoreBoardRepository.create(newMatch);
    }

    @Override
    public void finishGame(UUID matchId) {
        if (!scoreBoardRepository.deleteById(matchId)) {
            throw new MatchNotFoundException(matchId);
        }
    }

    @Override
    public Match updateScore(MatchUpdateRequest matchUpdateRequest) {
        return scoreBoardRepository.update(matchUpdateRequest);
    }

    @Override
    public List<Match> getSummaryByTotalScore() {
        return scoreBoardRepository.getAll().stream()
                .sorted(Comparator.comparing(Match::getTotalScore)
                        .reversed()
                        .thenComparing(Comparator.comparing(Match::getCreatedAt).reversed()))
                .toList();
    }
}
