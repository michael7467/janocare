package com.janocare.auth.application.commands.location;

import java.util.UUID;

public class DeleteCityCommand {

    public UUID cityId;

    public DeleteCityCommand() {}

    public DeleteCityCommand(UUID cityId) {
        this.cityId = cityId;
    }
}