package saludity.patient.Persistence.DTO;

import lombok.Data;

@Data
public class PatientProfileDTO extends PatientRequestDTO {
    private byte age;
}
