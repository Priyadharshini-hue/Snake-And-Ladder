import Models.Ladder;
import Models.Player;
import Models.Snake;
import Services.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Driver {
    public static void main(String[] args) {
        FastReader input = new FastReader();

        // snakes
        int noOfSnake = input.nextInt();
        List<Snake> snakes = new ArrayList<>();
        for (int i = 0; i < noOfSnake; i++) {
            snakes.add(new Snake(input.nextInt(), input.nextInt()));
        }

        // ladders
        int noOfLadder = input.nextInt();
        List<Ladder> ladders = new ArrayList<>();
        for (int i = 0; i < noOfLadder; i++) {
            ladders.add(new Ladder(input.nextInt(), input.nextInt()));
        }

        int noOfPlayers = input.nextInt();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < noOfPlayers; i++) {
            players.add(new Player(input.next()));
        }

        Game game = new Game();

        game.setSnakes(snakes);
        game.setLadders(ladders);
        game.setPlayers(players);

        game.startGame();
    }
}

class FastReader {
    BufferedReader br;
    StringTokenizer st;

    public FastReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() {
        return Integer.parseInt(next());
    }
}