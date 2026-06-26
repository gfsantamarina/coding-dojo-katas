# coding-dojo-katas

Katas for my Coding Dojo work.

First Kata - http://codingdojo.org/kata/Bowling/<br>
I used the Bowling Score Calculator (http://www.bowlinggenius.com/) to verify my sample data.

## Bowling Game Scorer

Reads a file of bowling games (one game per line) and prints the calculated score for each.

### Input format

Each line is a sequence of space-separated frame tokens:

- `X` — a strike
- `5/` — a spare (first ball 5, second ball completes 10)
- `9-` — a miss (first ball 9, second ball 0)
- `11` — an open frame (first ball 1, second ball 1)

Example: `X X X X X X X X X X X X` scores 300 (a perfect game). See `tests/testcase1.txt` for more.

### Project layout

```
src/main/java/com/gsantamarina/bowlinggame/
    BowlingGameScorer.java       # Entry point — reads input file, prints scores
    model/
        BowlingGame.java         # Parses a game line into frames, computes total score
        Frame.java               # A single frame with strike/spare scoring logic
src/test/java/...                # JUnit 5 tests
tests/testcase1.txt              # Sample input with expected scores
```

## Building and Running

Requires a JDK (Java 11+). With [Maven](https://maven.apache.org/) installed:

```bash
mvn compile
java -cp target/classes com.gsantamarina.bowlinggame.BowlingGameScorer tests/testcase1.txt
```

## Running Tests

```bash
mvn test
```

Without Maven, see `CLAUDE.md` for a manual compile-and-test workflow using the JUnit standalone launcher.
