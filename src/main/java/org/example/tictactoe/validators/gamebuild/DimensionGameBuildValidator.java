package org.example.tictactoe.validators.gamebuild;

import org.example.tictactoe.constants.GameConstants;
import org.example.tictactoe.dtos.ResponseStatus;
import org.example.tictactoe.dtos.ValidatorResponse;
import org.example.tictactoe.models.Game;

import static org.example.tictactoe.constants.GameConstants.MAX_ALLOWED_DIMENSION;

public class DimensionGameBuildValidator implements GameBuildValidator {

    @Override
    public ValidatorResponse validate(Game.GameBuilder gameBuilder) {
        ValidatorResponse response = new ValidatorResponse();

        int dimension = gameBuilder.getDimension();
        if (dimension >= GameConstants.MIN_ALLOWED_DIMENSION &&
                dimension <= GameConstants.MAX_ALLOWED_DIMENSION) {
            response.setStatus(ResponseStatus.SUCCESS);
            response.setMessage("Valid Dimensions");
        } else {
            response.setStatus(ResponseStatus.SUCCESS);
            response.setMessage("Invalid Dimensions");
        }
        return response;
    }
}
