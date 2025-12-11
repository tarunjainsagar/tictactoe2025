package org.example.tictactoe.factories;

import org.example.tictactoe.models.BotDifficultyLevel;
import org.example.tictactoe.strategies.bot.BotPlayingStrategy;
import org.example.tictactoe.strategies.bot.EasyBotPlayingStrategy;
import org.example.tictactoe.strategies.bot.HardBotPlayingStrategy;
import org.example.tictactoe.strategies.bot.MediumBotPlayingStrategy;

public class BotPlayingFactory {


    public static BotPlayingStrategy getPlayingStrategy(BotDifficultyLevel botDifficultyLevel) {
        return switch (botDifficultyLevel) {
            case EASY -> new EasyBotPlayingStrategy();
            case MEDIUM -> new MediumBotPlayingStrategy();
            case HARD -> new HardBotPlayingStrategy();
        };
    }
}
