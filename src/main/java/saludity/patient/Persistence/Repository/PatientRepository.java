package saludity.patient.Persistence.Repository;

import org.springframework.data.repository.CrudRepository;
import saludity.patient.Model.Pojo.PatientEntity;

import java.util.Optional;

public interface PatientRepository extends CrudRepository<PatientEntity,Long> {
    Optional <PatientEntity> findPatientByci(String Ci);
}
