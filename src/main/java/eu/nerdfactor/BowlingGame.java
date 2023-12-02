package eu.nerdfactor;

import java.util.ArrayList;
import java.util.List;

/**
 * A bowling game that contains an amount of rolls that can knock over pins.
 */
public class BowlingGame {

	/**
	 * All Frames of the game.
	 */
	private final List<Frame> frames;

	public BowlingGame() {
		this.frames = new ArrayList<>();
	}

	/**
	 * Executes the next roll in the game. The result of the roll
	 * are the knocked over pins, which will be recorded in order to
	 * count the score.
	 *
	 * @param knockedOverPins The amount of pins that where knocked over in the roll.
	 */
	public void nextRoll(int knockedOverPins) {
		Frame lastFrame;
		if (!this.frames.isEmpty()) {
			lastFrame = this.frames.get(this.frames.size() - 1);
			if (lastFrame.isPlayed()) {
				lastFrame = new Frame();
				this.frames.add(lastFrame);
			}
		} else {
			lastFrame = new Frame();
			this.frames.add(lastFrame);
		}
		lastFrame.nextRoll(knockedOverPins);
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
		int totalScore = 0;
		for (Frame frame : this.frames) {
			totalScore += frame.countScore();
		}
		return totalScore;
	}
}
