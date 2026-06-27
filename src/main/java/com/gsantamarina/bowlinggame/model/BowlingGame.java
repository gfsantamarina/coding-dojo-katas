package com.gsantamarina.bowlinggame.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class BowlingGame {
	
	List <Frame> frames = new ArrayList<>();

	private void linkFrames() {
		// Link one frame to the next one
		for (int i=8; i>=0; i--)
			frames.get(i).setNextFrame(frames.get(i+1));
	}
	
	private int parseBall(char c, int previousPins) {
		if (c == 'X') return 10;
		if (c == '/') return 10 - previousPins;
		if (c == '-') return 0;
		return Character.getNumericValue(c);
	}

	private Frame parseFrame(int frameNumber, String[] tokens) {
		Frame aFrame = new Frame(frameNumber);
		String token = tokens[frameNumber];

		int first = parseBall(token.charAt(0), 0);
		aFrame.setFirstBall(first);

		if (first == 10) {
			aFrame.setStrike(true);
			if (aFrame.isLastFrame()) {
				int second = parseBall(tokens[10].charAt(0), 0);
				aFrame.setSecondBall(second);
				aFrame.setThirdBall(parseBall(tokens[11].charAt(0), second));
			}
			return aFrame;
		}

		int second = parseBall(token.charAt(1), first);
		aFrame.setSecondBall(second);
		if (token.charAt(1) == '/') aFrame.setSpare(true);

		if (aFrame.isLastFrame() && token.length() == 3)
			aFrame.setThirdBall(parseBall(token.charAt(2), second));

		return aFrame;
	}

	private void parseFramesFromLine(String aGameLine) {
		String[] tokens = aGameLine.split(" ", 13);
		for (int i = 0; i <= 9; i++)
			frames.add(parseFrame(i, tokens));
	}
		
	public int score() {
		return frames.stream().collect(Collectors.summingInt(Frame::score));
	}

	public String toString() {
		
		StringBuilder sb = new StringBuilder(50);
		
		sb.append(this.getClass().getName() + "[");
		Iterator<Frame> i = frames.iterator();
		
		while (i.hasNext()) {
			String aString = i.next().toString();
			sb.append(aString);
			if (i.hasNext()) sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

	public BowlingGame(String aGameLine) {
		parseFramesFromLine(aGameLine);
		linkFrames();
	}

}
