package org.example.tictactoe.strategies.bot;

import org.example.tictactoe.models.BotPlayer;
import org.example.tictactoe.models.Game;

public interface BotPlayingStrategy {
    Game makeMove(Game game, BotPlayer botPlayer);
}
