package domain.entities;

import domain.valueobjects.SpecialtyName;

import java.util.UUID;

public class Specialization {
    private final UUID id;
    private final SpecialtyName name;

    public Specialization(UUID id, SpecialtyName name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() { return id; }
    public SpecialtyName getName() { return name; }
}
