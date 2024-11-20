package com.example.exeption;

import java.util.UUID;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(UUID id) {
        super("Match not found by id: " + id);
    }
}
