package eu.nerdfactor.bowling;

import eu.nerdfactor.bowling.exceptions.MaxAmountOfRollsExceededException;
import eu.nerdfactor.bowling.exceptions.WrongAmountOfPinsException;

import java.util.Arrays;

public class App {
	public static void main(String[] args) throws NumberFormatException, WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		System.out.println("Bowling Game");
		if (args.length > 0) {
			int[] rolls = Arrays.stream(args[0].split(","))
					.mapToInt(Integer::parseInt)
					.toArray();
			BowlingGame game = new TenPinBowlingGame();
			game.nextRolls(rolls);
			int totalScore = game.countScore();
			System.out.println("Total score of the game is: " + totalScore);
		} else {
			System.out.println("Please provide all rolls of the game in a comma separated string.");
		}
	}
}