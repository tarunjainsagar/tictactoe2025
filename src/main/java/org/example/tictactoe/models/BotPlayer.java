package org.example.tictactoe.models;

import org.example.tictactoe.factories.BotPlayingFactory;
import org.example.tictactoe.strategies.bot.BotPlayingStrategy;

public class BotPlayer extends Player {

    private BotDifficultyLevel botDifficultyLevel;

    private BotPlayingStrategy botPlayingStrategy;

    public BotPlayer(String name,
                     char symbol,
                     BotDifficultyLevel botDifficultyLevel) {

        super(name, symbol);
        this.setPlayerType(PlayerType.BOT);
        this.botDifficultyLevel = botDifficultyLevel;
        this.botPlayingStrategy = BotPlayingFactory.getPlayingStrategy(botDifficultyLevel);
    }

    public BotDifficultyLevel getBotDifficultyLevel() {
        return botDifficultyLevel;
    }

    public BotPlayingStrategy getBotPlayingStrategy() {
        return botPlayingStrategy;
    }
}
