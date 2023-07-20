package Services;

import java.util.Random;

public class Dice {
    private int diceCount;

    Dice(int diceCount) {
        this.diceCount = diceCount;
    }

    public int roll() {
        int total = 0;
        Random random = new Random();
        for (int i = 0; i < this.diceCount; i++) {
            total += random.nextInt(6) + 1;
        }
        return total;
    }
}
