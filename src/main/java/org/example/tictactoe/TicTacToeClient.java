package org.example.tictactoe;

import org.example.tictactoe.controllers.GameController;
import org.example.tictactoe.controllers.GameResponseDto;
import org.example.tictactoe.dtos.*;
import org.example.tictactoe.models.BotDifficultyLevel;
import org.example.tictactoe.models.BotPlayer;
import org.example.tictactoe.models.GameState;
import org.example.tictactoe.models.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.example.tictactoe.constants.GameConstants.MAX_ALLOWED_DIMENSION;
import static org.example.tictactoe.constants.GameConstants.MIN_ALLOWED_DIMENSION;

public class TicTacToeClient {

    private static Scanner in = new Scanner(System.in);

    static void main() {

        GameStartRequestDto startRequestDto = getGameInputs();

        GameController gameController = new GameController();
        GameStartResponseDto responseDto = gameController.startGame(startRequestDto);

        if (responseDto.getStatus() == ResponseStatus.FAILURE) {
            System.out.println("Failed to start the game due to: " + responseDto.getMessage());
            return;
        }

        int gameId = responseDto.getGameId();
        printMessage("Game Begins!!!");

        GameRequestDto gameRequestDto = new GameRequestDto(gameId);
        GameState gameState = gameController.getGameState(gameRequestDto).getGameState();
        while (gameState == GameState.IN_PLAY) {
            System.out.println("=================================");
            gameController.displayBoard(gameRequestDto);

            GamePlayerResponseDto nextPlayer = gameController.getNextPlayer(gameRequestDto);
            if (nextPlayer.getStatus() == ResponseStatus.FAILURE) {
                System.out.println("Failed to get next player: " + nextPlayer.getStatus());
                return;
            }

            System.out.println(nextPlayer.getPlayer().getName() + " : Do you wish to undo last move ? y/n");
            char isUndo = in.next().charAt(0);
            if (isUndo == 'y') {
                GameResponseDto gameResponseDto = gameController.undoLastPlayerMove(gameRequestDto);
                if (gameResponseDto.getStatus() == ResponseStatus.FAILURE) {
                    System.out.println(gameResponseDto.getMessage());
                } else {
                    gameController.displayBoard(gameRequestDto);
                }
            }

            GameMoveResponseDto gameMoveResponseDto = gameController.makeMove(gameRequestDto);
            if (gameMoveResponseDto.getStatus() == ResponseStatus.FAILURE) {
                System.out.println(gameMoveResponseDto.getMessage());
            }
            gameState = gameController.getGameState(gameRequestDto).getGameState();
            System.out.println("=================================");
        }

        gameController.displayBoard(gameRequestDto);
        if (gameState == GameState.DRAW) {
            printMessage("Game is drawn. There is no winner.");
        } else if (gameState == GameState.WON) {
            Player winner = gameController.getWinner(gameRequestDto).getPlayer();
            printMessage(winner.getName() + " [" + winner.getSymbol() + " ] won the game.");
        }
    }

    private static GameStartRequestDto getGameInputs() {

        printMessage("Welcome to Tic-Tac-Toe Game");

        System.out.println("What is the dimension of the Game?");
        int dimension = in.nextInt();

        while (!(dimension >= MIN_ALLOWED_DIMENSION &&
                dimension <= MAX_ALLOWED_DIMENSION)) {
            System.out.println("Please provide valid dimension between " + MIN_ALLOWED_DIMENSION + " and " + MAX_ALLOWED_DIMENSION + " of the Game?");
            dimension = in.nextInt();
        }

        System.out.println("Is there a bot player? y/n: ");
        char isBot = in.next().charAt(0);

        List<Character> allowedInputs = Arrays.asList('y', 'n');
        while (!allowedInputs.contains(isBot)) {
            System.out.println("Please provide correct input. Is there a bot player? y/n: ");
            isBot = in.next().charAt(0);
        }

        int iterateForPlayerDetails = dimension - 1;
        if (isBot == 'y') {
            iterateForPlayerDetails = dimension - 2;
        }

        List<Character> usedSymbols = new ArrayList<>();
        List<Player> players = new ArrayList<>();

        int i = 0;
        for (; i < iterateForPlayerDetails; i++) {
            String playerName = inputPlayerName("Enter name of player " + (i + 1) + " : ", in);
            char playerSymbol = inputPlayerSymbol(i, in, usedSymbols);
            players.add(new Player(playerName, playerSymbol));
        }

        if (isBot == 'y') {
            String playerName = inputPlayerName("Enter name of BOT player : ", in);
            char playerSymbol = inputPlayerSymbol(i, in, usedSymbols);

            System.out.println("Enter difficulty level of BOT player : EASY/MEDIUM/HARD");
            BotDifficultyLevel difficultyLevel = BotDifficultyLevel.valueOf(in.next());

            players.add(new BotPlayer(playerName, playerSymbol, difficultyLevel));
        }

        GameStartRequestDto startRequestDto = new GameStartRequestDto();
        startRequestDto.setDimension(dimension);
        startRequestDto.setPlayers(players);
        return startRequestDto;
    }

    private static char inputPlayerSymbol(int i, Scanner in, List<Character> usedSymbols) {
        System.out.println("Enter symbol of player " + (i + 1) + " : ");
        char playerSymbol = in.next().charAt(0);

        while (usedSymbols.contains(playerSymbol)) {
            System.out.println("Symbol " + playerSymbol + " is not available. Enter symbol of player " + (i + 1) + " : ");
            playerSymbol = in.next().charAt(0);
        }

        usedSymbols.add(playerSymbol);
        return playerSymbol;
    }

    private static String inputPlayerName(String i, Scanner in) {
        System.out.println(i);
        String playerName = in.next();
        return playerName;
    }

    private static void printMessage(String x) {
        System.out.println("=============================");
        System.out.println(x);
        System.out.println("=============================");
    }
}
