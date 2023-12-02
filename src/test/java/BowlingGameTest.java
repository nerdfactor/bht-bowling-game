import eu.nerdfactor.BowlingGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BowlingGameTest {

	/**
	 * Check if a game without any knocked over pins has a score of zero.
	 */
	@Test
	void gameWithoutKnockedOverPinsHasScoreOfZero() {
		int expectedScore = 0;
		int amountOfRolls = 20;
		BowlingGame game = new BowlingGame();
		for (int i : new int[amountOfRolls]) {
			game.nextRoll(i);
		}
		int totalScore = game.countScore();
		Assertions.assertEquals(expectedScore, totalScore);
	}

	/**
	 * Check if the total score of the game is counted correctly using
	 * the same amount of knocked over pins for each roll.
	 */
	@Test
	void countTotalScoreOfKnockedOverPins() {
		int expectedScore = 60;
		int amountOfRolls = 20;
		BowlingGame game = new BowlingGame();
		for (int currentRoll = 0; currentRoll < amountOfRolls; currentRoll++) {
			game.nextRoll(3);
		}
		int totalScore = game.countScore();
		Assertions.assertEquals(expectedScore, totalScore);
	}
}
