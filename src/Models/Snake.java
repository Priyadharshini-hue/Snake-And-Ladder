package Models;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private final int start;
    private final int end;
    private final List<Snake> snakes;

    public Snake(int start, int end) {
        this.start = start;
        this.end = end;
        this.snakes = new ArrayList<>();
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    // Create snakes without user input
    public void addSnakes(int numberOfSnakes, int boardSize) {
        while (numberOfSnakes > 0) {
            int start = (int) (Math.random() * (boardSize * boardSize) + 1);

            // Generate a random position for the snake head that is different from the tail position
            int end;
            do {
                end = (int) (Math.random() * (boardSize * boardSize) + 1);
            } while (start == end);

            if (end >= start) {
                continue;
            }
            snakes.add(new Snake(start, end));
            numberOfSnakes--;
        }
    }
}