package eu.nerdfactor;

import eu.nerdfactor.exceptions.MaxAmountOfRollsExceededException;
import eu.nerdfactor.exceptions.WrongAmountOfPinsException;

/**
 * A bowling game that contains an amount of rolls that can knock over pins.
 * The knocked over pins will be counted by the specific scoring methods
 * of the games implementation.
 */
public interface BowlingGame {

	/**
	 * Executes the next roll in the game. The result of the roll
	 * are the knocked over pins, which will be recorded in order to
	 * count the score.
	 *
	 * @param knockedOverPins The amount of pins that where knocked over in the roll.
	 */
	void nextRoll(int knockedOverPins) throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException;

	/**
	 * Calculates the score for the game. It will check every frame knocked over pins and count
	 * their score accordingly. Can be called multiple times during the game and provides the
	 * correct score for the current game state.
	 *
	 * @return The current score for the game.
	 */
	int countScore();
}
