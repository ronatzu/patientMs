package saludity.patient.Data.DAO;

import org.springframework.data.repository.CrudRepository;
import saludity.patient.Model.Pojo.MedicalResumeEntity;

import java.util.List;

public interface MedicalResumeRepository extends CrudRepository<MedicalResumeEntity,Long> {

    List<MedicalResumeEntity> findAllByPatientEntityId(Long PatientID);
}
