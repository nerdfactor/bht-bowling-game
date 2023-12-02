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
}
