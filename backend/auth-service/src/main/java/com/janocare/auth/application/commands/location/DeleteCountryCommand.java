package com.janocare.auth.application.commands.location;

import java.util.UUID;

public class DeleteCountryCommand {

    public UUID countryId;

    public DeleteCountryCommand() {}

    public DeleteCountryCommand(UUID countryId) {
        this.countryId = countryId;
    }
}