package saludity.patient.Persistence.Repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicalResumeRepository extends CrudRepository<MedicalResumeEntity,Long> {

    List<MedicalResumeEntity> findAllByPatientEntityId(Long PatientID);
}
