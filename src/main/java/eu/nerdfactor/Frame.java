package eu.nerdfactor;

/**
 * Frame within a {@link BowlingGame}.
 */
public class Frame {

	/**
	 * The pins that are knocked over in this Frame.
	 */
	private final int[] knockedOverPins = new int[2];

	/**
	 * Knock over some pins for this Frame.
	 *
	 * @param knockedOverPins The pins that should be knocked over.
	 */
	public void nextRoll(int knockedOverPins) {
		if (this.knockedOverPins[0] != 0) {
			this.knockedOverPins[1] = knockedOverPins;
		} else {
			this.knockedOverPins[0] = knockedOverPins;
		}
	}

	/**
	 * Count the score of this frame.
	 *
	 * @return The score of this frame.
	 */
	public int countScore() {
		return this.knockedOverPins[0] + this.knockedOverPins[1];
	}

	/**
	 * Check if the Frame is played.
	 *
	 * @return True if the Frame is played.
	 */
	public boolean isPlayed() {
		return this.knockedOverPins[1] != 0;
	}
}
