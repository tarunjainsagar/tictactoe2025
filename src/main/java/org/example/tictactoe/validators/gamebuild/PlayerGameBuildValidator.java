package org.example.tictactoe.validators.gamebuild;

import org.example.tictactoe.dtos.ResponseStatus;
import org.example.tictactoe.dtos.ValidatorResponse;
import org.example.tictactoe.models.Game;
import org.example.tictactoe.models.Player;
import org.example.tictactoe.models.PlayerType;

import java.util.HashSet;

public class PlayerGameBuildValidator implements GameBuildValidator {

    private final int MAX_ALLOWED_BOT = 1;

    @Override
    public ValidatorResponse validate(Game.GameBuilder gameBuilder) {

        ValidatorResponse response = new ValidatorResponse();
        response.setStatus(ResponseStatus.SUCCESS);
        response.setMessage("Valid Players");

        if (gameBuilder.getPlayers().size() != gameBuilder.getDimension() - 1) {
            response.setStatus(ResponseStatus.FAILURE);
            response.setMessage("Invalid number of players found for the board");
        }

        int botCount = 0;
        HashSet<Character> symbols = new HashSet<Character>();

        for (Player player : gameBuilder.getPlayers()) {
            if (player.getPlayerType() == PlayerType.BOT) {
                botCount++;
            }
            symbols.add(player.getSymbol());
        }

        if (botCount > MAX_ALLOWED_BOT) {
            response.setStatus(ResponseStatus.FAILURE);
            response.setMessage("Invalid number of bots found for the board. " +
                    "Maximum allowed is " + MAX_ALLOWED_BOT + " bot(s).");
        }

        if (gameBuilder.getPlayers().size() != symbols.size()) {
            response.setStatus(ResponseStatus.FAILURE);
            response.setMessage("Invalid number of symbols found for the board. ");
        }

        return response;
    }
}
