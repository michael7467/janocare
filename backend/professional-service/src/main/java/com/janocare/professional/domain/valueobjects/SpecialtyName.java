package domain.valueobjects;

import java.util.Objects;

public class SpecialtyName {
    private final String name;

    public SpecialtyName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Specialty name cannot be empty");
        this.name = name;
    }

    public String getName() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecialtyName)) return false;
        SpecialtyName that = (SpecialtyName) o;
        return name.equalsIgnoreCase(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}
