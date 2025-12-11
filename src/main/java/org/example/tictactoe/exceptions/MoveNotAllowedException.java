package org.example.tictactoe.exceptions;

public class MoveNotAllowedException extends Throwable {
    public MoveNotAllowedException(String message) {
        super(message);
    }
}
