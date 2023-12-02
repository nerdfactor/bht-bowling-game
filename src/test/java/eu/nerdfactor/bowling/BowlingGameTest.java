package eu.nerdfactor.bowling;

import eu.nerdfactor.bowling.BowlingGame;
import eu.nerdfactor.bowling.TenPinBowlingGame;
import eu.nerdfactor.bowling.exceptions.MaxAmountOfRollsExceededException;
import eu.nerdfactor.bowling.exceptions.WrongAmountOfPinsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

class BowlingGameTest {

	/**
	 * Check if a game without any knocked over pins has a score of zero.
	 */
	@Test
	void gameWithoutKnockedOverPinsHasScoreOfZero() throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		int expectedScore = 0;
		BowlingGame game = new TenPinBowlingGame();
		for (int i : new int[TenPinBowlingGame.AMOUNT_OF_MAX_ROLLS]) {
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
	void countTotalScoreOfKnockedOverPins() throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		int expectedScore = 60;
		BowlingGame game = new TenPinBowlingGame();
		for (int currentRoll = 1; currentRoll < TenPinBowlingGame.AMOUNT_OF_MAX_ROLLS; currentRoll++) {
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
	void countTotalScoreOfGameWithAllStrikes() throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		int expectedScore = 300;
		int amountOfFrames = TenPinBowlingGame.AMOUNT_OF_FRAMES + 2;
		BowlingGame game = new TenPinBowlingGame();
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
	void countTotalScoreOfRandomKnockedOverPins() throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException{
		int expectedScore = 0;
		BowlingGame game = new TenPinBowlingGame();
		for (int currentRoll = 1; currentRoll < TenPinBowlingGame.AMOUNT_OF_MAX_ROLLS; currentRoll += 2) {
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
	private int rollRandomTestFrame(BowlingGame game) throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		int possibleKnockedOverPinsWithoutStrikes = TenPinBowlingGame.AMOUNT_OF_PINS - 1;
		int randomKnockedOverPins = new Random().nextInt(0, possibleKnockedOverPinsWithoutStrikes);
		int pinsInFirstRoll = randomKnockedOverPins / 2;
		int pinsInSecondRoll = randomKnockedOverPins - pinsInFirstRoll;
		game.nextRoll(pinsInFirstRoll);
		game.nextRoll(pinsInSecondRoll);
		return randomKnockedOverPins;
	}

	/**
	 * Check if the total score of the game is counted correctly in the
	 * edge case that the last frame is a spare. This must include one
	 * 21st roll as a bonus roll.
	 */
	@Test
	void countTotalScoreWithLastFrameSpare() throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		int expectedScore = 68;
		int amountOfRollsWithoutLastFrame = TenPinBowlingGame.AMOUNT_OF_MAX_ROLLS - 2;
		BowlingGame game = new TenPinBowlingGame();
		for (int currentRoll = 1; currentRoll < amountOfRollsWithoutLastFrame; currentRoll++) {
			game.nextRoll(3);
		}
		// roll a spare in the last frame
		game.nextRoll(TenPinBowlingGame.AMOUNT_OF_PINS / 2);
		game.nextRoll(TenPinBowlingGame.AMOUNT_OF_PINS / 2);

		// and add a 21st bonus roll
		game.nextRoll(4);

		int totalScore = game.countScore();
		Assertions.assertEquals(expectedScore, totalScore);
	}

	/**
	 * Check if the total score of the game is counted correctly in the
	 * edge case that the last frame is a strike. This must include one
	 * 21st roll as a bonus roll.
	 */
	@Test
	void countTotalScoreWithLastFrameStrike() throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		int expectedScore = 71;
		int amountOfRollsWithoutLastFrame = TenPinBowlingGame.AMOUNT_OF_MAX_ROLLS - 2;
		BowlingGame game = new TenPinBowlingGame();
		for (int currentRoll = 1; currentRoll < amountOfRollsWithoutLastFrame; currentRoll++) {
			game.nextRoll(3);
		}
		// roll a strike in the last frame
		game.nextRoll(TenPinBowlingGame.AMOUNT_OF_PINS);

		// add the normal 20th roll
		game.nextRoll(3);

		// and add a 21st bonus roll
		game.nextRoll(4);

		int totalScore = game.countScore();
		Assertions.assertEquals(expectedScore, totalScore);
	}

	/**
	 * Check if the total score of the game is counted correctly in the
	 * edge case that the last frame is a strike by comparing to different
	 * bonus rolls.
	 */
	@Test
	void gameCorrectlyCountsBonusRollInLastFrame() throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		BowlingGame firstGame = new TenPinBowlingGame();
		int[] firstFullGameWithBonusRoll = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 10, 3, 4};
		for (int i : firstFullGameWithBonusRoll) {
			firstGame.nextRoll(i);
		}
		int firstGameTotalScore = firstGame.countScore();

		BowlingGame secondGame = new TenPinBowlingGame();
		int[] secondFullGameWithBonusRoll = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 10, 3, 2};
		for (int i : secondFullGameWithBonusRoll) {
			secondGame.nextRoll(i);
		}
		int secondGameTotalScore = secondGame.countScore();

		int scoreDifferenceBetweenGames = secondGameTotalScore - firstGameTotalScore;
		int expectedScoreDifferenceBetweenGames = secondFullGameWithBonusRoll[secondFullGameWithBonusRoll.length - 1] - firstFullGameWithBonusRoll[firstFullGameWithBonusRoll.length - 1];

		Assertions.assertNotEquals(firstGameTotalScore, secondGameTotalScore);
		Assertions.assertEquals(scoreDifferenceBetweenGames, expectedScoreDifferenceBetweenGames);
	}

	/**
	 * Check if the game won't allow wrong amounts of knocked over pins
	 * in a roll. Wrong amounts of knocked over pins are either negativ
	 * or exceeding the {@link TenPinBowlingGame#AMOUNT_OF_PINS maximum amount of pins}.
	 */
	@Test
	void gameWontAllowWrongAmountOfKnockedOverPins() {
		TenPinBowlingGame game = new TenPinBowlingGame();
		Assertions.assertThrows(WrongAmountOfPinsException.class, () -> game.nextRoll(TenPinBowlingGame.AMOUNT_OF_PINS + 1));
		Assertions.assertThrows(WrongAmountOfPinsException.class, () -> game.nextRoll(-1));
	}

	/**
	 * Check if a game won't allow to exceed the
	 * {@link TenPinBowlingGame#AMOUNT_OF_MAX_ROLLS maximum amount of rolls}.
	 */
	@Test
	void gameWontExceedMaxAmountOfRolls() {
		int amountOfRolls = TenPinBowlingGame.AMOUNT_OF_MAX_ROLLS + 1;
		TenPinBowlingGame game = new TenPinBowlingGame();
		Assertions.assertThrows(MaxAmountOfRollsExceededException.class, () -> {
			for (int currentRoll : new int[amountOfRolls]) {
				game.nextRoll(currentRoll);
			}
		});
	}

}
