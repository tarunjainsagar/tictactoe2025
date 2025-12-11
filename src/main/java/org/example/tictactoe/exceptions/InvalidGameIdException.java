package org.example.tictactoe.exceptions;

public class InvalidGameIdException extends Exception {
    public InvalidGameIdException(String gameNotFound) {
        super(gameNotFound);
    }
}
