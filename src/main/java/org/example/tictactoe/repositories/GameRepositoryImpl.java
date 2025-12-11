package org.example.tictactoe.repositories;

import org.example.tictactoe.models.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameRepositoryImpl implements GameRepository {

    private static Map<Integer, Game> gameDb = new HashMap<>();
    private static int GAME_ID = 0;

    public Game save(Game game) {
        /*
        * upsert = works as insert or update
        * */
        int gameId;
        if (game.getId() == 0) {
            gameId = ++GAME_ID;
            game.setId(gameId);
        } else {
            gameId = game.getId();
        }
        gameDb.put(gameId, game);
        return gameDb.get(gameId);
    }

    public Optional<Game> findById(int gameId) {
        return Optional.ofNullable(gameDb.get(gameId));
    }

}
