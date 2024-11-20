package com.example.service;

import com.example.exeption.MatchNotFoundException;
import com.example.exeption.MatchValidationException;
import com.example.model.MatchUpdateRequest;
import com.example.repository.ScoreBoardRepositoryInMemoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardServiceImplTest {

    private ScoreBoardService scoreBoardService;

    @BeforeEach
    public void setUp() {
        this.scoreBoardService = new ScoreBoardServiceImpl(new ScoreBoardRepositoryInMemoryImpl());
    }

    @Test
    void shouldStartNewGame() {
        //given
        //when
        var result = scoreBoardService.startGame("Portugal", "Poland");

        //then
        assertEquals("Portugal", result.getHomeTeamName());
        assertEquals(0, result.getHomeTeamScore());
        assertEquals("Poland", result.getAwayTeamName());
        assertEquals(0, result.getAwayTeamScore());
        assertEquals(1, scoreBoardService.getSummaryByTotalScore().size());
    }

    @Test
    void shouldThrowValidationExceptionWhenStartGameWithNullOrEmptyTeamNames() {
        //given
        //when
        //then
        assertThrows(MatchValidationException.class, () -> scoreBoardService.startGame("", "Poland"));
        assertThrows(MatchValidationException.class, () -> scoreBoardService.startGame(" ", "Poland"));
        assertThrows(MatchValidationException.class, () -> scoreBoardService.startGame(null, "Poland"));
        assertThrows(MatchValidationException.class, () -> scoreBoardService.startGame("Portugal", ""));
        assertThrows(MatchValidationException.class, () -> scoreBoardService.startGame("Portugal", " "));
        assertThrows(MatchValidationException.class, () -> scoreBoardService.startGame("Portugal", null));
    }

    @Test
    void shouldFinishGame() {
        //given
        scoreBoardService.startGame("Portugal", "Poland");
        var matches = scoreBoardService.getSummaryByTotalScore();

        //when
        scoreBoardService.finishGame(matches.getFirst().getId());

        //then
        assertTrue(scoreBoardService.getSummaryByTotalScore().isEmpty());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenTryToFinishNotExistingGame() {
        //given
        scoreBoardService.startGame("Portugal", "Poland");
        var matches = scoreBoardService.getSummaryByTotalScore();

        //when
        //then
        assertThrows(MatchNotFoundException.class, () -> scoreBoardService.finishGame(UUID.randomUUID()));
    }

    @Test
    void shouldUpdateScore() {
        //given
        var game = scoreBoardService.startGame("Portugal", "Poland");

        //when
        var result = scoreBoardService.updateScore(new MatchUpdateRequest(game.getId(), 5, 1));

        //then
        assertEquals("Portugal", result.getHomeTeamName());
        assertEquals(5, result.getHomeTeamScore());
        assertEquals("Poland", result.getAwayTeamName());
        assertEquals(1, result.getAwayTeamScore());
    }

    @Test
    void shouldGetSummary() throws InterruptedException {
        //given
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

        //when
        var result = scoreBoardService.getSummaryByTotalScore();

        //then
        assertEquals("Uruguay", result.get(0).getHomeTeamName());
        assertEquals("Italy", result.get(0).getAwayTeamName());

        assertEquals("Spain", result.get(1).getHomeTeamName());
        assertEquals("Brazil", result.get(1).getAwayTeamName());

        assertEquals("Mexico", result.get(2).getHomeTeamName());
        assertEquals("Canada", result.get(2).getAwayTeamName());

        assertEquals("Argentina", result.get(3).getHomeTeamName());
        assertEquals("Australia", result.get(3).getAwayTeamName());

        assertEquals("Germany", result.get(4).getHomeTeamName());
        assertEquals("France", result.get(4).getAwayTeamName());
    }
}
