package com.gsantamarina.bowlinggame;

import com.gsantamarina.bowlinggame.model.BowlingGame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BowlingGameTest {

    private int score(String game) {
        return new BowlingGame(game).score();
    }

    @Test
    void spareEveryFrameWithFive() {
        assertEquals(150, score("5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/5"));
    }

    @Test
    void missEverySecondBall() {
        assertEquals(90, score("9- 9- 9- 9- 9- 9- 9- 9- 9- 9-"));
    }

    @Test
    void perfectGame() {
        assertEquals(300, score("X X X X X X X X X X X X"));
    }

    @Test
    void elevenStrikesAndNine() {
        assertEquals(299, score("X X X X X X X X X X X 9"));
    }

    @Test
    void elevenStrikesAndFive() {
        assertEquals(295, score("X X X X X X X X X X X 5"));
    }

    @Test
    void mixedGameEndingWithThreeStrikes() {
        assertEquals(96, score("1- 2- 3- 4- 5- 6- 7- 8- X X X X"));
    }

    @Test
    void spareEveryFrameWithFiveAndSix() {
        assertEquals(151, score("5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/6"));
    }

    @Test
    void lastFrameSpareWithMiss() {
        assertEquals(140, score("5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5-"));
    }

    @Test
    void openingSpareFollowedByMixes() {
        assertEquals(107, score("1/ 2- 3- 4- 5- 6- 7- 8- X X X X"));
    }

    @ParameterizedTest(name = "first frame \"{0}\" scores {1}")
    @CsvSource({
        "'11 2- 3- 4- 5- 6- 7- 8- X X X X', 97",
        "'12 2- 3- 4- 5- 6- 7- 8- X X X X', 98",
        "'13 2- 3- 4- 5- 6- 7- 8- X X X X', 99",
        "'14 2- 3- 4- 5- 6- 7- 8- X X X X', 100",
        "'15 2- 3- 4- 5- 6- 7- 8- X X X X', 101",
        "'16 2- 3- 4- 5- 6- 7- 8- X X X X', 102",
        "'17 2- 3- 4- 5- 6- 7- 8- X X X X', 103",
        "'18 2- 3- 4- 5- 6- 7- 8- X X X X', 104"
    })
    void twoDigitFirstFrameWithStrikesAtEnd(String game, int expected) {
        assertEquals(expected, score(game));
    }
}
