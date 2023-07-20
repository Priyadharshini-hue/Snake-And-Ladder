package Services;

import Models.Ladder;
import Models.Player;
import Models.Snake;
import Models.Board;

import java.util.*;

public class Game {
    private Board board;
    private int numberOfPlayers;
    private Queue<Player> players;

    private final int boardSize = 100;
    private final int NumberOfDices = 100;

    public Game() {
        this.board = new Board(boardSize);
        this.players = new LinkedList<>();
    }
    /*  if number of dices and board size is changeable
    public void setNumberOfDices(int numberOfDices) {
        this.numberOfDices = numberOfDices;
    }
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }
    */

    // setters
    public void setSnakes(List<Snake> snakes) {
        this.board.setSnakes(snakes);
    }

    public void setLadders(List<Ladder> ladders) {
        this.board.setLadders(ladders);
    }

    public void setPlayers(List<Player> players) {
        this.numberOfPlayers = players.size();
        Map<String, Integer> playerPieces = new HashMap<>();
        for (Player player : players) {
            this.players.offer(player);
            playerPieces.put(player.getName(), 0);
        }
        board.setPlayerPieces(playerPieces);
    }

    public void startGame() {
        while (!isGameCompleted()) {
            Player currentPlayer = players.poll();
            int diceValue = getCurrentDiceRollValue();

            movePlayerPosition(currentPlayer, diceValue);
            if (isPlayerWon(currentPlayer)) {
                System.out.println("Player " + currentPlayer.getName() + " won the game!!");
                this.board.getPlayerPieces().remove(currentPlayer.getName());
            } else {
                this.players.offer(currentPlayer);
            }
        }
    }

    private boolean isPlayerWon(Player player) {
        return this.board.getPlayerPieces().get(player.getName()) == this.boardSize;
    }

    private void movePlayerPosition(Player player, int diceValue) {
        int currentPosition = this.board.getPlayerPieces().get(player.getName());
        int nextPosition = currentPosition + diceValue;

        System.out.println(currentPosition + " " + nextPosition);

        if (nextPosition > this.boardSize) {
            nextPosition = currentPosition;
        } else {
            nextPosition = getNewPositionWithSnakeAndLadders(nextPosition);
        }
       // System.out.println(currentPosition + " " + nextPosition);

        this.board.getPlayerPieces().put(player.getName(), nextPosition);

        System.out.println("Player: " + player.getName() + " moved from position " + currentPosition + " to "
                + nextPosition);
    }

    private int getNewPositionWithSnakeAndLadders(int nextPosition) {
        for (Snake snake : this.board.getSnakes()) {
            if (snake.getStart() == nextPosition) {
                nextPosition = snake.getEnd();
                break; // Apply only one snake effect.
            }
        }
        System.out.println(nextPosition);
        for (Ladder ladder : this.board.getLadders()) {
            if (ladder.getStart() == nextPosition) {
                nextPosition = ladder.getEnd();
                break; // Apply only one ladder effect.
            }
        }
        System.out.println(nextPosition);

        return nextPosition;
    }

    private int getCurrentDiceRollValue() {
        return Dice.roll();
    }

    private boolean isGameCompleted() {
        return this.players.size() < this.numberOfPlayers;
    }
}

