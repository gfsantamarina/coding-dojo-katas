# CLAUDE.md

Project context and conventions for AI-assisted development sessions.

## Project Overview

Bowling score calculator kata. Reads a file of bowling game strings and prints the score for each.

- Kata reference: http://codingdojo.org/kata/Bowling/
- Sample input: `tests/testcase1.txt`

## Project Structure

```
src/main/java/com/gsantamarina/bowlinggame/
    BowlingGameScorer.java       # Main entry point — reads input file, prints scores
    model/
        BowlingGame.java         # Parses a game line into frames, computes total score
        Frame.java               # Models a single frame with strike/spare scoring logic

src/test/java/com/gsantamarina/bowlinggame/
    BowlingGameTest.java         # JUnit 5 tests covering all testcase1.txt scenarios

tests/
    testcase1.txt                # Sample input with expected scores in comments

pom.xml                          # Maven build config with JUnit Jupiter 5.10.2
```

## Java Environment

JDK is installed at:
```
/Library/Java/JavaVirtualMachines/temurin-25.jdk/Contents/Home
```

Maven is NOT installed. Use the JUnit standalone launcher for running tests (see below).

## Building

Compile main sources:
```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-25.jdk/Contents/Home
export PATH="$JAVA_HOME/bin:$PATH"
mkdir -p target/classes
javac -d target/classes \
  src/main/java/com/gsantamarina/bowlinggame/model/Frame.java \
  src/main/java/com/gsantamarina/bowlinggame/model/BowlingGame.java \
  src/main/java/com/gsantamarina/bowlinggame/BowlingGameScorer.java
```

Compile tests (requires JUnit standalone jar — see Running Tests):
```bash
javac -cp target/classes:/tmp/junit-standalone.jar \
  -d target/test-classes \
  src/test/java/com/gsantamarina/bowlinggame/BowlingGameTest.java
```

## Running Tests

Download the JUnit standalone launcher (one-time):
```bash
curl -L -o /tmp/junit-standalone.jar \
  "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.2/junit-platform-console-standalone-1.10.2.jar"
```

Run all tests:
```bash
java -jar /tmp/junit-standalone.jar \
  --class-path target/classes:target/test-classes \
  --scan-class-path
```

Once Maven is installed, the above can be replaced with simply:
```bash
mvn test
```

## Running the Program

```bash
java -cp target/classes com.gsantamarina.bowlinggame.BowlingGameScorer tests/testcase1.txt
```

## Git & GitHub

- Remote: `https://github.com/gfsantamarina/coding-dojo-katas.git`
- GitHub credentials are stored in the macOS Keychain. Pushing works from the user's terminal but not from sandboxed agent terminals — ask the user to push if the agent cannot.

## .gitignore

The following are excluded from version control:
- `target/` — Maven build output
- `out/` — legacy manual build output
- `*.class` — compiled Java files
- `.DS_Store` — macOS system files
- `.vscode/` — personal editor settings

## Known Bug History

**Fixed:** `Frame.getSecondBallForScore()` previously returned the wrong bonus ball when the 9th frame was a strike and the 10th frame also opened with a strike. The fix changed `if (isStrike)` to `if (isStrike && !isLastFrame())`. Do not revert this change.

## Test Coverage

`BowlingGameTest.java` covers all 20 lines of `testcase1.txt` (17 test cases — some lines are duplicate game strings). Tests include a `@ParameterizedTest` for the two-digit frame description cases (lines 8–15).
