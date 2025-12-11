package org.example.tictactoe.repositories;

import org.example.tictactoe.models.Game;

import java.util.Optional;

public interface GameRepository {

    Game save(Game game);

    Optional<Game> findById(int gameId);
}
