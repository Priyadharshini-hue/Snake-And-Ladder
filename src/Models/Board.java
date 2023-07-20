package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private int size;
    private List<Ladder> ladders;
    private List<Snake> snakes;
    private Map<String, Integer> playerPieces;
    private List<Player> players;

    public Board(int size) {
        this.size = size;
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
        this.players = new ArrayList<>();
        this.playerPieces = new HashMap<>();
    }

    public void setSnakes(List<Snake> snakes) {
        this.snakes = snakes;
    }

    public void setLadders(List<Ladder> ladders) {
        this.ladders = ladders;
    }

    public List<Snake> getSnakes() {
        return snakes;
    }

    public List<Ladder> getLadders() {
        return ladders;
    }

    public void setPlayerPieces(Map<String, Integer> playerPieces) {
        this.playerPieces = playerPieces;
    }

    public Map<String, Integer> getPlayerPieces() {
        return playerPieces;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
