#!/bin/bash

FLAG=".claude/.test-needed"

if [ ! -f "$FLAG" ]; then
  exit 0
fi

rm "$FLAG"

export JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-25.jdk/Contents/Home
export PATH="$JAVA_HOME/bin:$PATH"

mkdir -p target/classes target/test-classes

javac -d target/classes \
  src/main/java/com/gsantamarina/bowlinggame/model/Frame.java \
  src/main/java/com/gsantamarina/bowlinggame/model/BowlingGame.java \
  src/main/java/com/gsantamarina/bowlinggame/BowlingGameScorer.java 2>&1

if [ $? -ne 0 ]; then
  echo "❌ COMPILATION FAILED — fix errors before proceeding" >&2
  exit 0
fi

javac -cp target/classes:/tmp/junit-standalone.jar \
  -d target/test-classes \
  src/test/java/com/gsantamarina/bowlinggame/BowlingGameTest.java 2>&1

if [ $? -ne 0 ]; then
  echo "❌ TEST COMPILATION FAILED — fix errors before proceeding" >&2
  exit 0
fi

OUTPUT=$(java -jar /tmp/junit-standalone.jar \
  --class-path target/classes:target/test-classes \
  --scan-class-path 2>&1)

PASSED=$(echo "$OUTPUT" | grep -oP '\[\K[0-9]+(?= tests successful\])')
FAILED=$(echo "$OUTPUT" | grep -oP '\[\K[0-9]+(?= tests failed\])')

FAILED=${FAILED:-0}

if [ "$FAILED" -eq 0 ]; then
  echo "✅ All ${PASSED} tests passed."
else
  echo "❌ ${FAILED} test(s) FAILED (${PASSED} passed)."
  echo "$OUTPUT" | grep -A3 "FAILED"
fi
