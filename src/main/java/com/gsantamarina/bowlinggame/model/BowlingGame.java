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
	
	private Frame newFrameFromFrameToken(int frameNumber, String frameDesc ) {
		
		Frame aFrame = new Frame(frameNumber);
		
		if (frameDesc.charAt(0)=='X') {
			aFrame.setStrike(true);
			aFrame.setFirstBall(10);
			return aFrame;
		}
		if (Character.isDigit(frameDesc.charAt(0))) {
			aFrame.setFirstBall(Character.getNumericValue(frameDesc.charAt(0)));
		}
		if (frameDesc.length()>1) {
			if (Character.isDigit(frameDesc.charAt(1))) {
				aFrame.setSecondBall(Character.getNumericValue(frameDesc.charAt(1)));
			}
			if (frameDesc.charAt(1)=='/') {
				aFrame.setSecondBall(10-aFrame.getFirstBall());
				aFrame.setSpare(true);
			}
			if (frameDesc.charAt(1)=='-') {
				aFrame.setSecondBall(0);
				return aFrame;
			}
		}
		
		// Must have been a spare in the last frame
		if (aFrame.isLastFrame() && frameDesc.length()==3) {
			if (Character.isDigit(frameDesc.charAt(2))) {
				aFrame.setThirdBall(Character.getNumericValue(frameDesc.charAt(2)));
			}
		}

		return aFrame;
	}
	
	private Frame newFrameFromFrameTokens(int frameNumber, String firstBall, String secondBall, String thirdBall) {
		Frame aFrame = newFrameFromFrameToken(frameNumber, firstBall);
			
		if (firstBall.charAt(0)=='X') {
			aFrame.setStrike(true);
			aFrame.setFirstBall(10);
		}

		if (secondBall.charAt(0)=='X') {
			aFrame.setStrike(true);
			aFrame.setSecondBall(10);
		}

		if (thirdBall.charAt(0)=='X') {
			aFrame.setStrike(true);
			aFrame.setThirdBall(10);
		}
		
		if (Character.isDigit(thirdBall.charAt(0))) {
			aFrame.setThirdBall(Character.getNumericValue(thirdBall.charAt(0)));
		}
		return aFrame;
	}
	
	private void parseFramesFromLine(String aGameLine)
	{
		String tokens[] = aGameLine.split(" ", 13);
		Frame aFrame=null;
		
		for (int i=0; i<=9; i++) {
			if (i<9)
				aFrame = newFrameFromFrameToken(i, tokens[i]);
			else if (i==9) // Handle the special case of the last frame and balls - weird
				if (tokens[9].length()==3 && (tokens[9].charAt(1)=='/') ||
					tokens[9].length()==2 && (tokens[9].charAt(1)=='-')	)
						aFrame = newFrameFromFrameToken(i, tokens[i]);
				else
					aFrame = newFrameFromFrameTokens(i, tokens[i], tokens[i+1], tokens[i+2]);
			frames.add(aFrame);
		}
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
