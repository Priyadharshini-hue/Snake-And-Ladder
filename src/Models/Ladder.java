package Models;

import java.util.ArrayList;
import java.util.List;

public class Ladder {
    private final int start;
    private final int end;
    private final List<Ladder> ladders;

    public Ladder(int start, int end) {
        this.start = start;
        this.end = end;
        this.ladders = new ArrayList<>();
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void addLadders(int noOfLadders, int boardSize) {
        while (noOfLadders > 0) {
            int start = (int) (Math.random() * (boardSize * boardSize) + 1);

            int end;
            do {
                end = (int) (Math.random() * (boardSize * boardSize) + 1);
            } while (end == start);

            if (start >= end) {
                continue;
            }
            ladders.add(new Ladder(start, end));
            noOfLadders--;
        }
    }
}
