package Services;

import Models.Ladder;
import Models.Player;
import Models.Snake;
import Models.Board;

import java.util.*;

public class Game {
    private Board board;
    private Dice dice;
    private int numberOfPlayers;
    private Queue<Player> players;

    private static final int BOARD_SIZE = 100;
    private static final int NUMBER_OF_DICES = 1;

    // Consecutive sixes - only if numberOfDices is 1
    private int previousPositionBeforeSixes;
    private int consecutiveSixes; // To keep track of consecutive sixes rolled by a player
    private static final int MAX_CONSECUTIVE_SIXES = 3; // Maximum allowed consecutive sixes
    private boolean extraTurn; // To keep track if a player gets an extra turn after rolling a six

    private boolean ShouldGameContinueTillLastPlayer; // should the game continue till last player

    public Game() {
        this.dice = new Dice(NUMBER_OF_DICES);
        this.board = new Board(BOARD_SIZE);
        this.players = new LinkedList<>();
        // Consecutive sixes
        this.consecutiveSixes = 0;
        this.extraTurn = false;
        this.SetShouldGameContinueTillLastPlayer(false);
    }

    /*  if number of dices and board size is changeable
    public void setNumberOfDices(int numberOfDices) {
        this.numberOfDices = numberOfDices;
    }
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }
    */
    // should game want to continue till last player
    public void SetShouldGameContinueTillLastPlayer(boolean ShouldGameContinueTillLastPlayer) {
        this.ShouldGameContinueTillLastPlayer = ShouldGameContinueTillLastPlayer;
    }

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
            int diceValue = getCurrentDiceRollValue();
            Player currentPlayer;

            if (diceValue == 6) {
                currentPlayer = players.peek();
                this.extraTurn = true;
                if (consecutiveSixes == 0) {
                    previousPositionBeforeSixes = board.getPlayerPieces().get(currentPlayer != null ? currentPlayer.getName() : null);

                }
                this.consecutiveSixes++;
            } else {
                currentPlayer = players.poll();
                if (extraTurn) {
                    this.extraTurn = false;
                    this.consecutiveSixes = 0;
                }
            }

            if (currentPlayer != null) {
                movePlayerPosition(currentPlayer, diceValue);
                if (isPlayerWon(currentPlayer)) {
                    System.out.println("Player " + currentPlayer.getName() + " won the game!!");
                    this.board.getPlayerPieces().remove(currentPlayer.getName());
                } else {
                    if (!extraTurn) {
                        this.players.offer(currentPlayer);
                    }
                }
            }
        }
    }

    private boolean isPlayerWon(Player player) {
        return this.board.getPlayerPieces().get(player.getName()) == BOARD_SIZE;
    }

    private void movePlayerPosition(Player player, int diceValue) {
        int currentPosition = this.board.getPlayerPieces().get(player.getName());
        int nextPosition = currentPosition + diceValue;


        if (this.consecutiveSixes == MAX_CONSECUTIVE_SIXES) {
            System.out.println("Three consecutive sixes, so last three turns are cancelled");
            this.board.getPlayerPieces().put(player.getName(), this.previousPositionBeforeSixes);
            this.extraTurn = false;
            this.consecutiveSixes = 0;
            return;
        }

        if (nextPosition > BOARD_SIZE) {
            nextPosition = currentPosition;
        } else {
            nextPosition = getNewPositionWithSnakeAndLadders(nextPosition);
        }

        this.board.getPlayerPieces().put(player.getName(), nextPosition);

        System.out.println("Player: " + player.getName() + " moved from position " + currentPosition + " to " + nextPosition);

        if (extraTurn) {
            System.out.println("Player: " + player.getName() + " got extra turn.");
        }
    }

    private int getNewPositionWithSnakeAndLadders(int nextPosition) {
        for (Snake snake : this.board.getSnakes()) {
            if (snake.getStart() == nextPosition) {
                nextPosition = snake.getEnd();
                break; // Apply only one snake effect.
            }
        }

        for (Ladder ladder : this.board.getLadders()) {
            if (ladder.getStart() == nextPosition) {
                nextPosition = ladder.getEnd();
                break; // Apply only one ladder effect.
            }
        }

        return nextPosition;
    }

    private int getCurrentDiceRollValue() {
        return dice.roll();
    }

    private boolean isGameCompleted() {
        if (ShouldGameContinueTillLastPlayer) return this.players.size() != 0;
        return this.players.size() < this.numberOfPlayers;
    }
}

