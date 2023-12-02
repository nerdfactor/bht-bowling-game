package eu.nerdfactor.bowling;

import eu.nerdfactor.bowling.exceptions.MaxAmountOfRollsExceededException;
import eu.nerdfactor.bowling.exceptions.WrongAmountOfPinsException;

/**
 * Implementation of {@link BowlingGame} for ten pin bowling.
 */
public class TenPinBowlingGame implements BowlingGame {

	/**
	 * The amount of maximum rolls in a game.The player has 2 rolls in
	 * 10 frames each. The amount rolls can only exceed 20, if the last
	 * frame contains a spare or strike. This would result in one additional
	 * bonus roll.
	 */
	public static final int AMOUNT_OF_MAX_ROLLS = 21;

	/**
	 * The amount of possible frames in a game. Each frame will contain two
	 * rolls. There may be a bonus roll in an additional frame, if the last
	 * frame contains a spare or strike
	 */
	public static final int AMOUNT_OF_FRAMES = 10;

	/**
	 * The amount of pins a bowling game has.
	 */
	public static final int AMOUNT_OF_PINS = 10;

	/**
	 * The roll this game is on. Required to access the correct
	 * array element in the knockedOverPins.
	 */
	private int currentRoll;

	/**
	 * The amount of knocked over pins for each roll. The array will
	 * never exceed the maximum amount of rolls.
	 */
	private final int[] knockedOverPinsPerRoll;

	public TenPinBowlingGame() {
		this.currentRoll = 0;
		this.knockedOverPinsPerRoll = new int[AMOUNT_OF_MAX_ROLLS];
	}

	/**
	 * Executes the next roll in the game. The result of the roll
	 * are the knocked over pins, which will be recorded in order to
	 * count the score.
	 *
	 * @param knockedOverPins The amount of pins that where knocked over in the roll.
	 */
	@Override
	public void nextRoll(int knockedOverPins) throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		if (wouldKnockOverWrongAmountOfPins(knockedOverPins)) {
			throw new WrongAmountOfPinsException();
		}
		if (wouldExceedMaxRolls()) {
			throw new MaxAmountOfRollsExceededException();
		}
		this.knockedOverPinsPerRoll[this.currentRoll] = knockedOverPins;
		this.currentRoll++;
	}

	/**
	 * Executes multiple {@link #nextRoll(int)} in the game.
	 *
	 * @param knockedOverPins An array of the pins knocked over in the next rolls.
	 * @throws WrongAmountOfPinsException        If a wrong amount off knocked over pins was passed.
	 * @throws MaxAmountOfRollsExceededException If the maximum of rolls in the game was exceeded.
	 */
	public void nextRolls(int[] knockedOverPins) throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		for (int roll : knockedOverPins) {
			nextRoll(roll);
		}
	}

	/**
	 * Check if the amount of knocked over pins is not possible.
	 *
	 * @param knockedOverPins The amount of knocked over pins.
	 * @return True if the amount of knocked over pins are not possible.
	 */
	private boolean wouldKnockOverWrongAmountOfPins(int knockedOverPins) {
		return knockedOverPins < 0 || knockedOverPins > AMOUNT_OF_PINS;
	}

	/**
	 * Check if the roll would exceed the maximum amount of rolls.
	 *
	 * @return True if the maximum amount is exceeded.
	 */
	private boolean wouldExceedMaxRolls() {
		return this.currentRoll >= AMOUNT_OF_MAX_ROLLS;
	}

	/**
	 * Calculates the score for the game. It will check every frame for strikes, spares
	 * or open frames and count their score accordingly.
	 * Can be called multiple times during the game and provides the correct score for
	 * the current game state.
	 *
	 * @return The current score for the game.
	 */
	@Override
	public int countScore() {
		int currentScore = 0;
		int checkedRoll = 0;
		for (int frame = 0; frame < AMOUNT_OF_FRAMES; frame++) {
			if (isRollAStrike(checkedRoll)) {
				currentScore += countScoreForStrike(checkedRoll);
				checkedRoll++;
			} else if (isRollASpare(checkedRoll)) {
				currentScore += countScoreForSpare(checkedRoll);
				checkedRoll += 2;
			} else {
				currentScore += countScoreForOpenFrame(checkedRoll);
				checkedRoll += 2;
			}
		}
		return currentScore;
	}

	/**
	 * Check if a roll is a strike.
	 * A strike is a roll that knocks over all pins at once.
	 *
	 * @param roll The roll that should be checked.
	 * @return True if the roll is a strike.
	 */
	private boolean isRollAStrike(int roll) {
		return this.knockedOverPinsPerRoll[roll] == AMOUNT_OF_PINS;
	}

	/**
	 * Check if a roll is a spare.
	 * A spare is a roll where both rolls of a frame knock over all pins.
	 *
	 * @param roll The roll that should be checked.
	 * @return True if the roll is a spare.
	 */
	private boolean isRollASpare(int roll) {
		return this.knockedOverPinsPerRoll[roll] + this.knockedOverPinsPerRoll[roll + 1] == AMOUNT_OF_PINS;
	}

	/**
	 * Calculates the score for a strike.
	 * The score for a strike is counted by adding the knocked over pins (not the score) of
	 * the next two rolls to the amount of knocked over pins from the strike (always the maximum).
	 *
	 * @param roll The roll that should be checked. Assumes that the checked roll is the first roll in the frame.
	 * @return The score for the strike.
	 */
	private int countScoreForStrike(int roll) {
		int strikeBonus = this.knockedOverPinsPerRoll[roll + 1] + this.knockedOverPinsPerRoll[roll + 2];
		return AMOUNT_OF_PINS + strikeBonus;
	}

	/**
	 * Calculates the score for a spare.
	 * The score for a spare is counted by adding the knocked over pins (not the score) of
	 * the first roll in the next frame to the knocked over pins from the spare (always the maximum).
	 *
	 * @param roll The roll that should be checked. Assumes that the checked roll is the first roll in the frame.
	 * @return The score of the spare.
	 */
	private int countScoreForSpare(int roll) {
		int spareBonus = this.knockedOverPinsPerRoll[roll + 2];
		return AMOUNT_OF_PINS + spareBonus;
	}

	/**
	 * Calculates the score for an open frame.
	 * An open frame is a frame that not is a spare or strike. The score is calculated by
	 * adding the knocked over pins of both rolls.
	 *
	 * @param roll The roll that should be checked. Assumes that the checked roll is the first roll in the frame.
	 * @return The score of the open frame.
	 */
	private int countScoreForOpenFrame(int roll) {
		return this.knockedOverPinsPerRoll[roll] + this.knockedOverPinsPerRoll[roll + 1];
	}
}
