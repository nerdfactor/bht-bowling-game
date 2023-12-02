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
		BowlingGame game = new BowlingGame();
		for (int i : new int[BowlingGame.AMOUNT_OF_MAX_ROLLS]) {
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
		BowlingGame game = new BowlingGame();
		for (int currentRoll = 1; currentRoll < BowlingGame.AMOUNT_OF_MAX_ROLLS; currentRoll++) {
			game.nextRoll(3);
		}
		int totalScore = game.countScore();
		Assertions.assertEquals(expectedScore, totalScore);
	}

	/**
	 * Check if the total score of the game is counted correctly if all
	 * rolls are strikes. The amount of rolls will be one for each frame
	 * * and two additional bonus rolls for the last frame being a strike.
	 */
	@Test
	void countTotalScoreOfGameWithAllStrikes() {
		int expectedScore = 300;
		int amountOfFrames = BowlingGame.AMOUNT_OF_FRAMES + 2;
		BowlingGame game = new BowlingGame();
		for (int currentRoll = 0; currentRoll < amountOfFrames; currentRoll++) {
			game.nextRoll(10);
		}
		int totalScore = game.countScore();
		Assertions.assertEquals(expectedScore, totalScore);
	}

}
