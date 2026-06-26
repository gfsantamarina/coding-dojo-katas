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
    void gutterGame() {
        assertEquals(0, score("0- 0- 0- 0- 0- 0- 0- 0- 0- 0-"));
    }

    @Test
    void allOnes() {
        assertEquals(20, score("11 11 11 11 11 11 11 11 11 11"));
    }

    @Test
    void oneSpareRestGutter() {
        assertEquals(10, score("5/ 0- 0- 0- 0- 0- 0- 0- 0- 0-"));
    }

    @Test
    void oneStrikeRestGutter() {
        assertEquals(10, score("X 0- 0- 0- 0- 0- 0- 0- 0- 0-"));
    }

    @Test
    void lastFrameStrikeThenTwoNumericBalls() {
        // 10th-frame strike with numeric bonus balls 4 and 5: 10 + 4 + 5 = 19
        assertEquals(19, score("0- 0- 0- 0- 0- 0- 0- 0- 0- X 4 5"));
    }

    @Test
    void lastFrameStrikeThenSpareOnBonusBalls() {
        // 10th-frame strike with bonus balls 5 then spare: 10 + 5 + 5 = 20
        assertEquals(20, score("0- 0- 0- 0- 0- 0- 0- 0- 0- X 5 /"));
    }

    @Test
    void threeStrikesThenGutters() {
        // Turkey at the start: 30 + 20 + 10, rest gutters = 60
        assertEquals(60, score("X X X 0- 0- 0- 0- 0- 0- 0-"));
    }

    @Test
    void spareThenStrikeRestGutters() {
        // Spare (5/) bonus is the next ball (the strike's 10): 20 + 10 = 30
        assertEquals(30, score("5/ X 0- 0- 0- 0- 0- 0- 0- 0-"));
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
