package saludity.patient.Service.Impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saludity.patient.Persistence.DTO.PatientProfileDTO;
import saludity.patient.Persistence.Repository.PatientRepository;
import saludity.patient.Model.Pojo.PatientEntity;
import saludity.patient.Persistence.DTO.PatientRequestDTO;
import saludity.patient.Service.PatientService;
import saludity.patient.Persistence.Utils.PatchUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public PatientProfileDTO createPatient(PatientRequestDTO patientRequestDTO) {
        try {
            if (patientRequestDTO.getBirthday() == null) {
                throw new IllegalArgumentException("The birthday cannot be null");
            }


            PatientEntity patientEntity = objectMapper.convertValue(patientRequestDTO, PatientEntity.class);
            System.out.println(patientEntity);

            patientEntity.setCreationDate(LocalDateTime.now());
            patientEntity.setAge((byte)((Period.between(patientEntity.getBirthday(), LocalDate.now()).getYears())));
            System.out.println(patientEntity);
            patientRepository.save(patientEntity);

            return objectMapper.convertValue(patientEntity, PatientProfileDTO.class);

        }catch (Exception e){
            throw new UnsupportedOperationException("Error guardar usuario");
        }

    }

    @Override
    public PatientProfileDTO updatePatient(String patientci,PatientRequestDTO patientRequestDTO) {
        // Encuentra el paciente por su CI
        Optional<PatientEntity> optionalPatient = patientRepository.findPatientByci(patientci);
        if (optionalPatient.isPresent()) {
            try {
                PatientEntity existingPatient = optionalPatient.get();
                PatientEntity patchEntity = objectMapper.convertValue(patientRequestDTO, PatientEntity.class);
                PatchUtils.applyPatch(existingPatient, patchEntity);
                patientRepository.save(existingPatient);
                return objectMapper.convertValue(existingPatient, PatientProfileDTO.class);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Paciente no encontrado");
        }
    }


    //@Transactional(readOnly = true)
    @Override
    public PatientProfileDTO getPatient(String ci) {
         Optional <PatientEntity> patient=patientRepository.findPatientByci(ci);
         if (patient.isPresent()) {
             ModelMapper mapper = new ModelMapper();
             PatientEntity patientactual=patient.get();
             return mapper.map(patientactual, PatientProfileDTO.class);
         } else {
             return null;
         }

    }



}
