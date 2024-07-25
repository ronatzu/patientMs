package saludity.patient.Service;

import saludity.patient.Persistence.DTO.PatientRequestDTO;
import saludity.patient.Persistence.DTO.PatientProfileDTO;

public interface PatientService {

    PatientProfileDTO createPatient(PatientRequestDTO patientRequestDTO);

    PatientProfileDTO updatePatient(String patientId, PatientRequestDTO patientRequestDTO);

    PatientProfileDTO getPatient(String ci);

}
