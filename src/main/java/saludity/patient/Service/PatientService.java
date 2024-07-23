package saludity.patient.Service;

import saludity.patient.Model.Pojo.PatientEntity;
import saludity.patient.Model.Request.CreatePatientRequest;

import java.util.List;

public interface PatientService {

    PatientEntity createPatient(CreatePatientRequest request);


    PatientEntity updatePatient(String patientId, String request);

    PatientEntity getPatient(String ci);

}
