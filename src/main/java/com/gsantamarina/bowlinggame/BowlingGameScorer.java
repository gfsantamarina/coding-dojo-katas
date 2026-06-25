package com.gsantamarina.bowlinggame;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.gsantamarina.bowlinggame.model.BowlingGame;

public class BowlingGameScorer {

	private static BowlingGame parseLine(String readLine) {
	// "X X X X X X X X X X X X "
	// "9- 9- 9- 9- 9- 9- 9- 9- 9- 9-"
	// "5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/5"
		return new BowlingGame(readLine);
	}

	private static void scoreGameFile(BufferedReader aReader) throws IOException {
		String aLine;
		while ((aLine = aReader.readLine()) != null)
		{
			System.out.print(aLine);
			BowlingGame aGame = parseLine(aLine);
			System.out.println(" - Calculated score: " + aGame.score());
		}
	}

	public static void main(String[] args) {
	
		if (args.length!=1) {
			System.out.println("help: bowlinggame inputframesfile");
		} else {
			try {
				scoreGameFile(new BufferedReader(new InputStreamReader(new FileInputStream(args[0]))));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
