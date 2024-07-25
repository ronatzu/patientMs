package saludity.patient.Persistence.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class PatientRequestDTO implements Serializable {
    @NotEmpty(message = "First name cannot be empty")
    private String firstname;

    @NotEmpty(message = "Last name cannot be empty")
    private String lastname;


    @NotEmpty(message = "CI cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "CI must be 10 digits")
    @Size(min = 10, max = 10, message = "CI must be 10 characters long")
    private String ci;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Size(min = 10, max = 10, message = "Phone must be 10 characters long")
    private String phone;


    @NotEmpty(message = "Gender cannot be empty")
    private String gender;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Past(message = "Birthday must be a past date")
    private LocalDate birthday;
}
