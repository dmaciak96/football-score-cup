package com.example.repository;

import com.example.DataProvider;
import com.example.exeption.MatchNotFoundException;
import com.example.exeption.MatchValidationException;
import com.example.model.Match;
import com.example.model.MatchUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardRepositoryInMemoryImplTest {

    private ScoreBoardRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new ScoreBoardRepositoryInMemoryImpl(DataProvider.createInitialMatchList());
    }


    @Test
    void shouldAddNewMatchToList() {
        //given
        var matchToAdd = new Match("Portugal", 5, "Poland", 1);

        //when
        repository.create(matchToAdd);

        //then
        var allMatches = repository.getAll();
        assertEquals(6, allMatches.size());
        assertTrue(allMatches.contains(matchToAdd));
    }

    @Test
    void shouldThrowValidationExceptionIfAddMatchWithEmptyHomeTeamName() {
        //given
        var matchOne = new Match(null, 5, "Poland", 1);
        var matchTwo = new Match("", 5, "Poland", 1);
        var matchThree = new Match(" ", 5, "Poland", 1);

        //when
        //then
        assertThrows(MatchValidationException.class, () -> repository.create(matchOne));
        assertThrows(MatchValidationException.class, () -> repository.create(matchTwo));
        assertThrows(MatchValidationException.class, () -> repository.create(matchThree));
    }

    @Test
    void shouldThrowValidationExceptionIfAddMatchWithEmptyAwayTeamName() {
        //given
        var matchOne = new Match("Portugal", 5, null, 1);
        var matchTwo = new Match("Portugal", 5, "", 1);
        var matchThree = new Match("Portugal", 5, " ", 1);

        //when
        //then
        assertThrows(MatchValidationException.class, () -> repository.create(matchOne));
        assertThrows(MatchValidationException.class, () -> repository.create(matchTwo));
        assertThrows(MatchValidationException.class, () -> repository.create(matchThree));
    }

    @Test
    void shouldThrowValidationExceptionIfAddedMatchWithScoresLessThanZero() {
        //given
        var matchOne = new Match("Portugal", -1, "Poland", 1);
        var matchTwo = new Match("Portugal", 5, "Poland", -1);

        //when
        //then
        assertThrows(MatchValidationException.class, () -> repository.create(matchOne));
        assertThrows(MatchValidationException.class, () -> repository.create(matchTwo));
    }

    @Test
    void shouldUpdateMatchScoreInFirstMatch() {
        //given
        var originalMatch = (Match) repository.getAll().toArray()[0];
        var matchUpdateRequest = new MatchUpdateRequest(originalMatch.getId(), 10, 15);

        //when
        repository.update(matchUpdateRequest);

        //then
        var result = (Match) repository.getAll().toArray()[0];
        assertEquals(10, result.getHomeTeamScore());
        assertEquals(15, result.getAwayTeamScore());
        assertEquals("Canada", result.getAwayTeamName());
        assertEquals("Mexico", result.getHomeTeamName());
        assertEquals(originalMatch.getId(), result.getId());
    }

    @Test
    void shouldUpdateMatchScoreInMiddleMatch() {
        //given
        var originalMatch = (Match) repository.getAll().toArray()[2];
        var matchUpdateRequest = new MatchUpdateRequest(originalMatch.getId(), 10, 15);

        //when
        repository.update(matchUpdateRequest);

        //then
        var result = (Match) repository.getAll().toArray()[2];
        assertEquals(10, result.getHomeTeamScore());
        assertEquals(15, result.getAwayTeamScore());
        assertEquals("France", result.getAwayTeamName());
        assertEquals("Germany", result.getHomeTeamName());
        assertEquals(originalMatch.getId(), result.getId());
    }

    @Test
    void shouldUpdateMatchScoreInEndMatch() {
        //given
        var originalMatch = (Match) repository.getAll().toArray()[4];
        var matchUpdateRequest = new MatchUpdateRequest(originalMatch.getId(), 10, 15);

        //when
        repository.update(matchUpdateRequest);

        //then
        var result = (Match) repository.getAll().toArray()[4];
        assertEquals(10, result.getHomeTeamScore());
        assertEquals(15, result.getAwayTeamScore());
        assertEquals("Australia", result.getAwayTeamName());
        assertEquals("Argentina", result.getHomeTeamName());
        assertEquals(originalMatch.getId(), result.getId());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenTryToUpdateScoreForNotExistingMatch() {
        //given
        var matchToUpdate = new MatchUpdateRequest(UUID.randomUUID(), 10, 15);

        //when
        //then
        assertThrows(MatchNotFoundException.class, () -> repository.update(matchToUpdate));
    }

    @Test
    void shouldDeleteMatchIfExists() {
        //given
        var matchToDelete = (Match) repository.getAll().toArray()[2];

        //when
        var result = repository.deleteById(matchToDelete.getId());

        //then
        assertTrue(result);
        assertFalse(repository.getAll().contains(matchToDelete));
    }

    @Test
    void shouldNotDeleteMatchIfNotExists() {
        //given
        var matchToDelete = new Match("Portugal", 5, "Poland", 1);

        //when
        var result = repository.deleteById(matchToDelete.getId());

        //then
        assertFalse(result);
    }
}
