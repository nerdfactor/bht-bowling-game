package eu.nerdfactor;

public class BowlingGame {

	/**
	 * Executes the next roll in the game. The result of the roll
	 * are the knocked over pins, which will be recorded in order to
	 * count the score.
	 *
	 * @param knockedOverPins The amount of pins that where knocked over in the roll.
	 */
	public void nextRoll(int knockedOverPins) {

	}

	/**
	 * Calculates the score for the game. It will check every frame for strikes, spares
	 * or open frames and count their score accordingly.
	 * Can be called multiple times during the game and provides the correct score for
	 * the current game state.
	 *
	 * @return The current score for the game.
	 */
	public int countScore() {
		return 0;
	}
}
