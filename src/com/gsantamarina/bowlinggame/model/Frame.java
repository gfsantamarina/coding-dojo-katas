package com.gsantamarina.bowlinggame.model;

public class Frame {
	
	int frameNumber=0;
	int pinsKnocked[]= {0,0,0};
	Frame nextFrame = null;

	boolean isStrike;
	boolean isSpare;
	
	public Frame(int frameNumber) {
		this.frameNumber = frameNumber;
	}

	public boolean isLastFrame() {
		return getFrameNumber()==9;
	}

	public int getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
	}

	public int[] getPinsKnocked() {
		return pinsKnocked;
	}

	public void setPinsKnocked(int[] pinsKnocked) {
		this.pinsKnocked = pinsKnocked;
	}

	public Frame getNextFrame() {
		return nextFrame;
	}

	public void setNextFrame(Frame nextFrame) {
		this.nextFrame = nextFrame;
	}
	
	public int getFirstBall() {
		return pinsKnocked[0];
	}

	public void setFirstBall(int firstBall) {
		pinsKnocked[0] = firstBall;
	}

	public int getSecondBall() {
		return pinsKnocked[1];
	}

	public int getSecondBallForScore() {
		if (isStrike)
			return !isLastFrame() ? getNextFrame().getFirstBall() : getThirdBall();
		else
			return pinsKnocked[1];
	}

	public void setSecondBall(int secondBall) {
		pinsKnocked[1] = secondBall;
	}
	
	public int getThirdBall() {
		return pinsKnocked[2];
	}

	public void setThirdBall(int thirdBall) {
		pinsKnocked[2] = thirdBall;
	}

	public boolean isStrike() {
		return isStrike;
	}

	public void setStrike(boolean isStrike) {
		this.isStrike = isStrike;
	}

	public boolean isSpare() {
		return isSpare;
	}

	public void setSpare(boolean isSpare) {
		this.isSpare = isSpare;
	}
	
	public int score() {
		int runningScore=getFirstBall()+getSecondBall();
		if (isSpare() || isStrike())
		{
			runningScore=runningScore + (!isLastFrame() ? getNextFrame().getFirstBall() : getThirdBall());
		}
		if (isStrike() && !isLastFrame())
		{
			runningScore=runningScore + getNextFrame().getSecondBallForScore();
		}
		return runningScore;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(20);

		sb.append("Frame[" + getFrameNumber() + "](");
		if (isStrike) sb.append("Strike)");
		if (isSpare) {
			sb.append(getFirstBall() + "-Spare");
			if (getThirdBall()>0) sb.append("-" + getThirdBall());
			sb.append(")");
		}
		else sb.append(getFirstBall() +":" + getSecondBall()+ ")");

		return sb.toString();
	}

}
