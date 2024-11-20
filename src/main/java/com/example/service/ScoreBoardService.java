package com.example.service;

import com.example.model.Match;
import com.example.model.MatchUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface ScoreBoardService {
    Match startGame(String homeTeamName, String awayTeamName);

    void finishGame(UUID matchId);

    Match updateScore(MatchUpdateRequest matchUpdateRequest);

    List<Match> getSummaryByTotalScore();
}
