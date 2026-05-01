package domain.entities;

import domain.valueobjects.Rating;

import java.util.UUID;

public class ProfessionalReview {

    private final UUID id;
    private final Rating rating;
    private final String comment;

    public ProfessionalReview(UUID id, Rating rating, String comment) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
    }

    public UUID getId() { return id; }
    public Rating getRating() { return rating; }
    public String getComment() { return comment; }
}
