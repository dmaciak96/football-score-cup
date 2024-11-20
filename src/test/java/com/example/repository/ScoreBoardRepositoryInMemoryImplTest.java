package com.example.repository;

import com.example.exeption.MatchNotFoundException;
import com.example.model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardRepositoryInMemoryImplTest {

    private ScoreBoardRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new ScoreBoardRepositoryInMemoryImpl(createInitialMatchList());
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
    void shouldUpdateMatchScoreInFirstMatch() {
        //given
        var matchToUpdate = (Match) repository.getAll().toArray()[0];
        matchToUpdate.setHomeTeamScore(10);
        matchToUpdate.setAwayTeamScore(15);

        //when
        repository.update(matchToUpdate);

        //then
        var result = (Match) repository.getAll().toArray()[0];
        assertEquals(10, result.getHomeTeamScore());
        assertEquals(15, result.getAwayTeamScore());
        assertEquals("Canada", result.getAwayTeamName());
        assertEquals("Mexico", result.getHomeTeamName());
        assertEquals(matchToUpdate.getId(), result.getId());
    }

    @Test
    void shouldUpdateMatchScoreInMiddleMatch() {
        //given
        var matchToUpdate = (Match) repository.getAll().toArray()[2];
        matchToUpdate.setHomeTeamScore(10);
        matchToUpdate.setAwayTeamScore(15);

        //when
        repository.update(matchToUpdate);

        //then
        var result = (Match) repository.getAll().toArray()[2];
        assertEquals(10, result.getHomeTeamScore());
        assertEquals(15, result.getAwayTeamScore());
        assertEquals("France", result.getAwayTeamName());
        assertEquals("Germany", result.getHomeTeamName());
        assertEquals(matchToUpdate.getId(), result.getId());
    }

    @Test
    void shouldUpdateMatchScoreInEndMatch() {
        //given
        var matchToUpdate = (Match) repository.getAll().toArray()[4];
        matchToUpdate.setHomeTeamScore(10);
        matchToUpdate.setAwayTeamScore(15);

        //when
        repository.update(matchToUpdate);

        //then
        var result = (Match) repository.getAll().toArray()[4];
        assertEquals(10, result.getHomeTeamScore());
        assertEquals(15, result.getAwayTeamScore());
        assertEquals("Australia", result.getAwayTeamName());
        assertEquals("Argentina", result.getHomeTeamName());
        assertEquals(matchToUpdate.getId(), result.getId());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenTryToUpdateScoreForNotExistingMatch() {
        //given
        var matchToUpdate = new Match("Portugal", 5, "Poland", 1);

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

    private List<Match> createInitialMatchList() {
        return List.of(
                new Match("Mexico", 0, "Canada", 5),
                new Match("Spain", 10, "Brazil", 2),
                new Match("Germany", 2, "France", 2),
                new Match("Uruguay", 6, "Italy", 6),
                new Match("Argentina", 3, "Australia", 1));
    }
}
