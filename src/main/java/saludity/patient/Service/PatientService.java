package saludity.patient.Service;

import saludity.patient.Model.Pojo.PatientEntity;
import saludity.patient.Data.DTO.PatientDTO;

public interface PatientService {

    PatientEntity createPatient(PatientDTO request);

    PatientEntity updatePatient(String patientId, String request);

    PatientEntity getPatient(String ci);

}
