package api.requests;

import jakarta.validation.constraints.NotBlank;

public class RegisterProfessionalRequest {

    @NotBlank(message = "First name is required")
    public String firstName;

    @NotBlank(message = "Last name is required")
    public String lastName;
}