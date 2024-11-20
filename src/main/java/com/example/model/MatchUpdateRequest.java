package com.example.model;

import java.util.UUID;

public record MatchUpdateRequest(UUID id,
                                 int homeTeamScore,
                                 int awayTeamScore) {
}
