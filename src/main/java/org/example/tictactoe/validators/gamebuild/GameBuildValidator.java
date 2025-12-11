package org.example.tictactoe.validators.gamebuild;

import org.example.tictactoe.dtos.ValidatorResponse;
import org.example.tictactoe.models.Game;

public interface GameBuildValidator {
    ValidatorResponse validate(Game.GameBuilder gameBuilder);
}
