package api.responses;

import java.util.List;
import java.util.UUID;

public class ProfessionalResponse {
    public UUID id;
    public String fullName;
    public String approvalStatus;
    public List<String> specializations;
    public List<String> reviews;
}
