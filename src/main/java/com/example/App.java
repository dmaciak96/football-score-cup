package com.example;

import com.example.model.MatchUpdateRequest;
import com.example.repository.ScoreBoardRepositoryInMemoryImpl;
import com.example.service.ScoreBoardServiceImpl;

public class App {
    public static void main(String[] args) throws InterruptedException {
        var scoreBoardService = new ScoreBoardServiceImpl(new ScoreBoardRepositoryInMemoryImpl());

        var gameOne = scoreBoardService.startGame("Mexico", "Canada");
        scoreBoardService.updateScore(new MatchUpdateRequest(gameOne.getId(), 0, 5));
        Thread.sleep(100);

        var gameTwo = scoreBoardService.startGame("Spain", "Brazil");
        scoreBoardService.updateScore(new MatchUpdateRequest(gameTwo.getId(), 10, 2));
        Thread.sleep(100);

        var gameThree = scoreBoardService.startGame("Germany", "France");
        scoreBoardService.updateScore(new MatchUpdateRequest(gameThree.getId(), 2, 2));
        Thread.sleep(100);

        var gameFour = scoreBoardService.startGame("Uruguay", "Italy");
        scoreBoardService.updateScore(new MatchUpdateRequest(gameFour.getId(), 6, 6));
        Thread.sleep(100);

        var gameFive = scoreBoardService.startGame("Argentina", "Australia");
        scoreBoardService.updateScore(new MatchUpdateRequest(gameFive.getId(), 3, 1));
        Thread.sleep(100);


        scoreBoardService.getSummaryByTotalScore().forEach(System.out::println);
    }
}
