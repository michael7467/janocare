package domain.entities;

import java.util.UUID;

public class SubSpecialization {
    private final UUID id;
    private final String name;

    public SubSpecialization(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
}
