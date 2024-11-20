package com.example.repository;

import com.example.model.Match;

import java.util.List;
import java.util.UUID;

public interface ScoreBoardRepository {
    Match create(Match match);

    Match update(Match match);

    boolean deleteById(UUID id);

    List<Match> getAll();
}
