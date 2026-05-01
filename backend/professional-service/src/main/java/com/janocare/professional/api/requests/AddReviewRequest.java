package api.requests;

import java.util.UUID;

public class AddReviewRequest {
    public UUID professionalId;
    public int rating;
    public String comment;
}
