package com.example.repository;

import com.example.model.Match;
import com.example.model.MatchUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface ScoreBoardRepository {
    Match create(Match match);

    Match update(MatchUpdateRequest matchUpdateRequest);

    boolean deleteById(UUID id);

    List<Match> getAll();
}
