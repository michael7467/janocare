package com.janocare.auth.application.commands.location;

import java.util.UUID;

public class DeleteStateCommand {

    public UUID stateId;

    public DeleteStateCommand() {}

    public DeleteStateCommand(UUID stateId) {
        this.stateId = stateId;
    }
}