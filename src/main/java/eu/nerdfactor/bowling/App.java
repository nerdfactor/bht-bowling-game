package eu.nerdfactor.bowling;

import eu.nerdfactor.bowling.exceptions.MaxAmountOfRollsExceededException;
import eu.nerdfactor.bowling.exceptions.WrongAmountOfPinsException;

import java.util.Arrays;

public class App {
	public static void main(String[] args) throws NumberFormatException, WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		printGameIntro();
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
		printGameOutro();
	}

	/**
	 * Print a simple intro image to the game.
	 */
	private static void printGameIntro() {
		System.out.println("              .-.");
		System.out.println("              \\ /      .-.");
		System.out.println("              |_|  .-. \\ /");
		System.out.println("              |=|  \\ / |_|");
		System.out.println("             /   \\ |_| |=|");
		System.out.println("            / (@) \\|=|/   \\");
		System.out.println("       ____ |     /   \\@)  \\");
		System.out.println("     .'    '.    / (@) \\   |");
		System.out.println("    / #      \\   |     |   |");
		System.out.println("    |    o o |'='|     |  /");
		System.out.println("    \\     o  /    \\   /'='");
		System.out.println("     '.____.'      '=");
		System.out.println("==================================");
		System.out.println("= Bowling Game ===================");
		System.out.println("==================================");
	}

	/**
	 * Print a simple outro to the game.
	 */
	private static void printGameOutro() {
		System.out.println("==================================");
	}
}