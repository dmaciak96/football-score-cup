package com.example;

import com.example.model.Match;

import java.util.List;

public class DataProvider {

    public static List<Match> createInitialMatchList() {
        return List.of(
                new Match("Mexico", 0, "Canada", 5),
                new Match("Spain", 10, "Brazil", 2),
                new Match("Germany", 2, "France", 2),
                new Match("Uruguay", 6, "Italy", 6),
                new Match("Argentina", 3, "Australia", 1));
    }
}
