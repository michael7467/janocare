package application.dto;

import java.util.List;
import java.util.UUID;

public class ProfessionalDTO {
    public UUID id;
    public String fullName;
    public String approvalStatus;
    public List<String> specializations;
    public List<String> availability;
    public List<String> reviews;
}
