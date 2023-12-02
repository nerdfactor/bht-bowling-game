import eu.nerdfactor.BowlingGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

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

	/**
	 * Check if the total score of the game is counted correctly using
	 * a random amount of knocked over pins without strikes.
	 */
	@Test
	void countTotalScoreOfRandomKnockedOverPins() {
		int expectedScore = 0;
		BowlingGame game = new BowlingGame();
		for (int currentRoll = 1; currentRoll < BowlingGame.AMOUNT_OF_MAX_ROLLS; currentRoll += 2) {
			expectedScore += this.rollRandomTestFrame(game);
		}
		int totalScore = game.countScore();
		Assertions.assertEquals(expectedScore, totalScore);
	}

	/**
	 * Rolls a complete frame with a randomly selected amount of knocked over pins
	 * split over two rolls. Makes sure that the frame won't contain a spare or
	 * a strike.
	 *
	 * @param game The game that the rolls will be for.
	 * @return The amount of randomly selected knocked over pins.
	 */
	private int rollRandomTestFrame(BowlingGame game) {
		int possibleKnockedOverPinsWithoutStrikes = BowlingGame.AMOUNT_OF_PINS - 1;
		int randomKnockedOverPins = new Random().nextInt(0, possibleKnockedOverPinsWithoutStrikes);
		int pinsInFirstRoll = randomKnockedOverPins / 2;
		int pinsInSecondRoll = randomKnockedOverPins - pinsInFirstRoll;
		game.nextRoll(pinsInFirstRoll);
		game.nextRoll(pinsInSecondRoll);
		return randomKnockedOverPins;
	}

}
